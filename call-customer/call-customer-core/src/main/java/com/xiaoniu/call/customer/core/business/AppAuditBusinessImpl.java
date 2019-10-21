package com.xiaoniu.call.customer.core.business;

import com.xiaoniu.architecture.page.api.bean.PageResult;
import com.xiaoniu.architecture.spring.boot.autoconfigure.redis.support.RedisRepository;
import com.xiaoniu.call.customer.api.business.AccountManageBusiness;
import com.xiaoniu.call.customer.api.business.AppAuditBusiness;
import com.xiaoniu.call.customer.api.dto.AccountManageDTO;
import com.xiaoniu.call.customer.api.dto.AppAuditDTO;
import com.xiaoniu.call.customer.api.dto.ManagerDTO;
import com.xiaoniu.call.customer.api.dto.UserLoginDTO;
import com.xiaoniu.call.customer.api.vo.AccountManagerVO;
import com.xiaoniu.call.customer.api.vo.AppAuditVO;
import com.xiaoniu.call.customer.api.vo.ManagerVO;
import com.xiaoniu.call.customer.core.cons.RedisCons;
import com.xiaoniu.call.customer.core.service.AccountManageService;
import com.xiaoniu.call.customer.core.service.AppAuditService;
import com.xiaoniu.call.customer.core.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class AppAuditBusinessImpl implements AppAuditBusiness {

    @Autowired
    private AppAuditService appAuditService;

    /**
     * 管理员管理
     *
     * @param managerVO
     * @return
     */
    @PostMapping(value = "/appAuditList")
    @Override
    public PageResult<AppAuditDTO> appAuditList(@RequestBody AppAuditVO managerVO) {
        return appAuditService.list(managerVO);
    }

    /**
     * 管理员新增
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/saveAppAudit")
    @Override
    public void saveAppAudit(@RequestBody AppAuditVO managerVO) {
        appAuditService.save(managerVO);
    }

    /**
     * 管理员启停用
     *
     * @param managerVO
     * @return
     */
    @PutMapping(value = "/updateAppAudit")
    @Override
    public void updateAppAudit(@RequestBody AppAuditVO managerVO) {
        appAuditService.update(managerVO);
    }

    /**
     * 管理员删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteAppAudit")
    @Override
    public void deleteAppAudit(@RequestParam("id") Long id) {
        appAuditService.delete(id);
    }

    @PostMapping(value = "/appAudit/channel")
    @Override
    public String channel() {
        return RedisRepository.hget(RedisCons.APP, RedisCons.APP_AUDIT_CHANNEL);
    }

    @PostMapping(value = "/appAudit/version")
    @Override
    public String version() {
        return RedisRepository.hget(RedisCons.APP, RedisCons.APP_AUDIT_VERSION);
    }

    /**
     *
     *
     * @param bigCode
     * @param smallCode
     * @return
     */
    @PostMapping(value = "/appAudit/dic")
    @Override
    public String dic(@RequestParam("bigCode") String bigCode, @RequestParam("smallCode") String smallCode) {
        return RedisRepository.hget(bigCode, smallCode);
    }

    /**
     * 刷新过审配置到redis
     *
     * @return
     */
    @PostMapping(value = "/appAudit/refresh")
    @Override
    public void refresh() {
        appAuditService.refresh();
    }
}
