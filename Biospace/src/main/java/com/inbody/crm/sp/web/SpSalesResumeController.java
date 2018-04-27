/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.sp.web;

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
import com.inbody.crm.sp.service.SpSalesResumeService;

/**
 * 销售计划履历Controller
 * 
 * @author yangj
 * @version 2017-10-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sp/resume")
public class SpSalesResumeController extends BaseController {

    @Autowired
    private SpSalesResumeService spSalesResumeService;

    @Autowired
    private AttachmentsService attachmentsService;

    /**
     * 销售计划履历画面初期取得
     */
    @RequiresPermissions("sp:resume:view")
    @RequestMapping(value = "list")
    public String getInitView(CoJobData search, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 查询分类：销售计划
        search.setType(CommonConstants.JOB_DATA_TYPE_1);
        // 采单订单list数据取得
        Page<CoJobData> page = spSalesResumeService
                .getResumeList(new Page<CoJobData>(request, response), search);
        model.addAttribute("page", page);
        model.addAttribute("searchForm", search);

        return "inbody/sp/sp001";
    }

    /**
     * 销售计划履历生成
     */
    @RequiresPermissions("sp:resume:edit")
    @RequestMapping(value = "execute")
    public String createResume(CoJobData search, HttpServletRequest request,
            HttpServletResponse response, Model model,
            RedirectAttributes redirectAttributes) {

        // 成交月是否输入
        if (search.getExpTurnoverMonth() == null) {
            this.addMessage(redirectAttributes, "执行失败：请输入执行月！");
            return "redirect:" + adminPath + "/sp/resume/list?repage";
        }

        // 手动执行生成销售计划履历
        try {
            spSalesResumeService.genSalesReport(search.getExpTurnoverMonth());
        } catch (Exception e) {
            e.printStackTrace();
            this.addMessage(redirectAttributes, "执行失败：发生系统内部错误！");
            return "redirect:" + adminPath + "/sp/resume/list?repage";
        }

        this.addMessage(redirectAttributes, "执行成功：销售计划履历生成成功！");
        return "redirect:" + adminPath + "/sp/resume/list?repage";
    }

    /**
     * 销售履历附件下载
     * 
     * @param fileId
     *            文件id
     * @param request
     *            请求信息
     * @param response
     *            响应信息
     */
    @RequiresPermissions("sp:resume:view")
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }
}