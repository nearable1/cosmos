/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.rm.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;
import com.inbody.crm.rm.domain.RmConsultingExcel;
import com.inbody.crm.rm.domain.RmConsultingSearch;
import com.inbody.crm.rm.entity.RmConsultingInfo;
import com.inbody.crm.rm.service.RmConsultingInfoService;

/**
 * 单表生成Controller
 * 
 * @author yangj
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/rm/consulting")
public class RmConsultingInfoController extends BaseController {

    @Autowired
    private RmConsultingInfoService rmConsultingInfoService;

    @RequiresPermissions("rm:consulting:view")
    @RequestMapping(value = { "list" })
    public String list(RmConsultingSearch search, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        Page<RmConsultingSearch> page = rmConsultingInfoService
                .findPageList(new Page<RmConsultingSearch>(request, response), search);
        model.addAttribute("search", search);
        model.addAttribute("page", page);
        return "inbody/rm/rm002";
    }

    @RequiresPermissions("rm:consulting:view")
    @RequestMapping(value = "form")
    public String form(@RequestParam(required = false) String id, Model model) {
        RmConsultingInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = rmConsultingInfoService.get(id);
            
            User user = UserUtils.getUser();
            if (StringUtils.equals(entity.getConsultingStatus(), "4") && StringUtils.equals(user.getId(), entity.getResponsiblePersonId())) {
            	entity = rmConsultingInfoService.updateConsultingStatus(entity);
            }
        }
        if (entity == null) {
            entity = new RmConsultingInfo();
            entity.setCreateBy(UserUtils.getUser());
            entity.setConsultingDate(new Date());
        }

        model.addAttribute("consultingInfo", entity);
        return "inbody/rm/rm001";
    }

    @RequiresPermissions("rm:consulting:edit")
    @RequestMapping(value = "save")
    public String save(RmConsultingInfo rmConsultingInfo, Model model) {
        if (!beanValidator(model, rmConsultingInfo)) {
            model.addAttribute("consultingInfo", rmConsultingInfo);
            return "inbody/rm/rm001";
        }

        try {
            RmConsultingInfo consultingInfo = rmConsultingInfoService
                    .saveConsulting(rmConsultingInfo);
            addMessage(model, "保存成功!");
            model.addAttribute("consultingInfo", consultingInfo);
            return "inbody/rm/rm001";
        } catch (ServiceException e) {
            addMessage(model, e.getMessage());
            model.addAttribute("consultingInfo", rmConsultingInfo);
            return "inbody/rm/rm001";
        }
    }

    @RequiresPermissions("rm:consulting:edit")
    @RequestMapping(value = "delete")
    public String delete(RmConsultingInfo rmConsultingInfo, Model model,
            RedirectAttributes redirectAttributes) {

        try {
            rmConsultingInfoService.deleteConsulting(rmConsultingInfo);
        } catch (ServiceException e) {
            addMessage(model, e.getMessage());
            model.addAttribute("consultingInfo", rmConsultingInfo);
            return "inbody/rm/rm001";
        }

        addMessage(redirectAttributes, "删除成功!");
        return "redirect:" + adminPath + "/rm/consulting/list?repage";
    }

    @RequiresPermissions("rm:consulting:view")
    @RequestMapping(value = "export")
    public String export(RmConsultingSearch search, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "无SN咨询一览_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<RmConsultingExcel> list = rmConsultingInfoService
                    .exportConsultingList(search);
            new ExportExcel("无S/N咨询一览", RmConsultingExcel.class).setDataList(list)
                    .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            addMessage(redirectAttributes, "导出无S/N咨询一览！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/rm/consulting/list?repage";
    }

}