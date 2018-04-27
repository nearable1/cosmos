/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.CoJobData;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.sm.service.SmStorageResumeService;

/**
 * 库存履历Controller
 * 
 * @author yangj
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sm/resume")
public class SpStorageResumeController extends BaseController {

    @Autowired
    private SmStorageResumeService smStorageResumeService;

    @Autowired
    private AttachmentsService attachmentsService;

    /**
     * 库存履历画面初期取得
     */
    @RequiresPermissions("sm:resume:view")
    @RequestMapping(value = "list")
    public String getInitView(CoJobData search, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 查询分类：库存履历
        search.setType(CommonConstants.JOB_DATA_TYPE_2);
        // 库存履历list数据取得
        Page<CoJobData> page = smStorageResumeService
                .getResumeList(new Page<CoJobData>(request, response), search);
        model.addAttribute("page", page);
        model.addAttribute("searchForm", search);

        return "sd/sm/sm005";
    }

    /**
     * 库存履历生成
     */
    @RequiresPermissions("sm:resume:edit")
    @RequestMapping(value = "execute")
    public String createResume(CoJobData search, Model model,
            RedirectAttributes redirectAttributes) {

        // 手动执行生成销售计划履历
        try {
            smStorageResumeService.genStorageReport(CommonConstants.GEN_METHOD_2);
        } catch (Exception e) {
            e.printStackTrace();
            this.addMessage(redirectAttributes, "执行失败：发生系统内部错误！");
            return "redirect:" + adminPath + "/sm/resume/list?repage";
        }

        this.addMessage(redirectAttributes, "执行成功：库存履历生成成功！");
        return "redirect:" + adminPath + "/sm/resume/list?repage";
    }

    /**
     * 库存履历附件下载
     * 
     * @param fileId
     *            文件id
     * @param request
     *            请求信息
     * @param response
     *            响应信息
     */
    @RequiresPermissions("sm:resume:view")
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }
}