package com.xiaoniu.walking.web.core.aspect;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoniu.walking.web.api.bo.FileUploadBO;
import com.xiaoniu.walking.web.core.model.auto.SysLog;
import com.xiaoniu.walking.web.core.model.auto.SysUser;
import com.xiaoniu.walking.web.core.service.SysLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 * @author xiangxianjin
 * @date 2019-03-27 14:49 PM
 */
@Aspect
@Configuration
public class WebOperationLogAspect {

    private static final Logger LOGGER = LogManager.getLogger(WebOperationLogAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    SysLogService sysLogService;

    @Pointcut("execution(public com.xiaoniu.architecture.commons.api.ResultBean com.xiaoniu.walking.web.core.controller.*.*(..))")
    public void point() {
    }

    /**
     * 执行交易之前新增交易信息
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "point()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        Subject subject = SecurityUtils.getSubject();
        SysUser user = (SysUser)subject.getPrincipal();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        StringBuffer requestLog = new StringBuffer();
        requestLog.append("请求信息：")
                .append("HTTP_METHOD = {" + request.getMethod() + "},\t")
                .append("CLASS_METHOD = {" + signature.getDeclaringTypeName() + "." + signature.getName() + "},\t");
        if(joinPoint.getArgs().length == 0) {
            requestLog.append("ARGS = {} ");
        } else {
            if (joinPoint.getArgs()[0] instanceof FileUploadBO) {
                FileUploadBO file = (FileUploadBO)joinPoint.getArgs()[0];
                requestLog.append("ARGS = {filePath:" + new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writeValueAsString(file.getFilePath()) + "}");
            } else {
                requestLog.append("ARGS = " + new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writeValueAsString(joinPoint.getArgs()[0]) + "");
            }
        }

        SysLog sysLog = new SysLog();
        sysLog.setUserId(user!= null ? user.getUserId() : "");
        sysLog.setIpAddress(request.getRemoteAddr());
        sysLog.setActionUrl(request.getRequestURI());
        sysLog.setActionParam(requestLog.toString());

        ActionLogger actionLogger = method.getAnnotation(ActionLogger.class);
        sysLog.setActionModule(actionLogger.moduleName());
        sysLog.setActionType(actionLogger.actionType());
        try {
            sysLogService.saveSysLog(sysLog);
        } catch (Exception e) {
            LOGGER.error("保存日志异常！异常信息：{}", e.getMessage(), e);
        }

        return joinPoint.proceed();
    }
}
