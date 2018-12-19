package io.club.common.aspect;

import com.google.gson.Gson;


import io.club.common.annotation.SysLog;
import io.club.common.annotation.SysLog;
import io.club.modules.sys.entity.SysDeptEntity;
import io.club.modules.sys.entity.SysLogEntity;
import io.club.modules.sys.entity.SysUserEntity;
import io.club.modules.sys.entity.VipEntity;
import io.club.modules.sys.service.SysDeptService;
import io.club.modules.sys.service.SysLogService;
import io.club.common.utils.HttpContextUtils;
import io.club.common.utils.IPUtils;

import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.3.0 2017-03-08
 */
@Aspect
@Component
public class SysLogAspect {
	@Autowired
	private SysLogService sysLogService;

	@Autowired
	private SysDeptService sysDeptService;
	
	@Pointcut("@annotation(io.club.common.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();

		//保存日志
		saveSysLog(point);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint) {

		SysLogEntity sysLog = new SysLogEntity();
		VipEntity vip = null;

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			vip = (VipEntity) args[0];
		}catch (Exception e){
			e.printStackTrace();
		}
		sysLog.setUsername(vip.getName());
		sysLog.setPhone(vip.getPhone());
		sysLog.setIdCard(vip.getCardNo());
		sysLog.setBalance(vip.getBalance());
		sysLog.setMoney(vip.getMoney());
		sysLog.setOperation(vip.getLatest());
		//门店
		Long storeId = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getDeptId();
		String store = sysDeptService.selectById(storeId).getName();

		sysLog.setStore(store);

		sysLog.setCreateDate(new Date());
		//保存系统日志
		sysLogService.insert(sysLog);
	}
}
