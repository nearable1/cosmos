package com.inbody.crm.sm.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.sm.domain.SmStorageSearch;
import com.inbody.crm.sm.domain.SmStorageSearchExcel;
import com.inbody.crm.sm.service.SmStorageSearchService;

/**
 * 库存查询Controller
 * 
 * @author yangj
 */
@Controller
@RequestMapping(value = "${adminPath}/sm/storage")
public class SmStorageSearchController extends BaseController {

    @Autowired
    private SmStorageSearchService smStorageSearchService;

    /**
     * 库存查询
     */
    @RequiresPermissions("sm:storage:view")
    @RequestMapping(value = "list")
    public String storageSearch(SmStorageSearch search, HttpServletRequest request,
            HttpServletResponse response, Model model) {

        // 库存信息分页查询
        Page<SmStorageSearch> page = smStorageSearchService
                .findStoragePage(new Page<SmStorageSearch>(request, response), search);

        model.addAttribute("searchForm", search);
        model.addAttribute("page", page);
        return "sd/sm/sm012";
    }

    /**
     * 库存excel导出
     */
    @RequiresPermissions("sm:storage:view")
    @RequestMapping(value = "export")
    public String storageExport(SmStorageSearch search, HttpServletRequest request,
            HttpServletResponse response, RedirectAttributes redirectAttributes) {

        try {
            String fileName = "库存一览_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
            List<SmStorageSearchExcel> list = smStorageSearchService
                    .exportStorageList(search);
            new ExportExcel("库存一览", SmStorageSearchExcel.class).setDataList(list)
                    .write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导出库存一览！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/sm/storage/list?repage";
    }

}
