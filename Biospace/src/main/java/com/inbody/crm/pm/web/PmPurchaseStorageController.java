/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.pm.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.service.ServiceException;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.ListUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.utils.excel.ImportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.pm.domain.PmPurStorageModel;
import com.inbody.crm.pm.domain.PmPurchaseStorage;
import com.inbody.crm.pm.domain.PmPurchaseStorageExcel;
import com.inbody.crm.pm.entity.PmPurchaseOrd;
import com.inbody.crm.pm.entity.PmPurchaseOrdDtl;
import com.inbody.crm.pm.service.PmPurchaseStorageService;

/**
 * 采购入库Controller
 * 
 * @author yangj
 * @version 2017-08-08
 */
@Controller
@RequestMapping(value = "${adminPath}/pm/storage")
public class PmPurchaseStorageController extends BaseController {

	@Autowired
	private PmPurchaseStorageService pmPurchaseStorageService;

	/**
	 * 采购订单入库画面初期取得
	 */
	@RequiresPermissions("pm:storage:view")
	@RequestMapping(value = "form")
	public String getInitView(Model model) {
		model.addAttribute("storagePur", new PmPurchaseOrd());
		return "inbody/pm/pm004";
	}

	/**
	 * 采购入库订单明细取得
	 */
	@RequiresPermissions("pm:storage:view")
	@RequestMapping(value = "list")
	public String getViewAndData(@RequestParam String purchaseNo, Model model) {
		return returnView(purchaseNo, null, model);
	}

	/**
	 * 保存采购入库信息
	 */
	@RequiresPermissions("pm:storage:edit")
	@RequestMapping(value = "save/{purchaseNo}")
	public String save(PmPurStorageModel storageModel,
			@PathVariable String purchaseNo, Model model) {

		// 采购订单号check
		if (StringUtils.isEmptyNull(purchaseNo)) {
			addMessage(model, "保存失败： 数据不整合！");
			return returnView(purchaseNo, null, model);
		}

		// 画面输入值验证
		if (!beanValidator(model, storageModel)) {
			// 还原画面输入值
			return returnView(purchaseNo, storageModel.getStoragePurList(), model);
		}
		
		try {
			pmPurchaseStorageService.saveStorageInfo(purchaseNo,
					storageModel.getStoragePurList());
		} catch (ServiceException e) {
		    e.printStackTrace();
			this.addMessage(model, e.getMessage());
			return returnView(purchaseNo, storageModel.getStoragePurList(), model);
		}

		addMessage(model, "采购入库信息保存成功！");
		return returnView(purchaseNo, null, model);
	}

	/**
	 * 下载采购到货入库明细表格
	 */
	@RequiresPermissions("pm:storage:view")
	@RequestMapping(value = "export")
	public String exportStorageFile(@RequestParam String purchaseNo,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "采购到货入库单_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";
			List<PmPurchaseStorageExcel> list = pmPurchaseStorageService
					.getPurStorageExportData(purchaseNo);
			new ExportExcel("采购到货入库单", PmPurchaseStorageExcel.class, 2)
					.setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "下载采购到货入库单！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pm/storage/list?purchaseNo=" + purchaseNo;
	}
	
	/**
	 * 上传采购到货入库明细表格
	 */
	@RequiresPermissions("pm:storage:edit")
	@RequestMapping(value = "import/{purchaseNo}", method = RequestMethod.POST)
    public String importStorageFile(PmPurStorageModel storageModel,
            @RequestParam(required = false) MultipartFile file,
            @PathVariable String purchaseNo, RedirectAttributes redirectAttributes,
            Model model) {

		// 采购订单号check
		if (StringUtils.isEmptyNull(purchaseNo)) {
			addMessage(model, "导入Excel失败： 数据不整合！");
			returnView(purchaseNo, null, model);
		}

		List<PmPurchaseStorageExcel> list = Lists.newArrayList();
		if (file != null) {
			if (file.isEmpty()) {
				addMessage(model, "导入Excel失败： 上传的文件为空，请上传采购入库单Excel文件！");
				returnView(purchaseNo, null, model);
			}
			
			try {
				ImportExcel ie = new ImportExcel(file, 1, 0);
				list = ie.getDataList(PmPurchaseStorageExcel.class);

				if (ListUtils.size(list) == 0) {
					addMessage(model, "导入Excel失败： 上传数据为空！");
					return returnView(purchaseNo, null, model);
				}
			} catch (Exception e) {
                e.printStackTrace();
                addMessage(redirectAttributes, "导入Excel失败：" + e.getMessage());
                return "redirect:" + adminPath + "/pm/storage/list?purchaseNo="
                        + purchaseNo;
			}
		}

        try {
            pmPurchaseStorageService.confirmPurStorage(storageModel.getUpdateDate(),
                    storageModel.getStoragePurList(), purchaseNo, list);
        } catch (ServiceException e) {
            e.printStackTrace();
            this.addMessage(model, e.getMessage());
            return returnView(purchaseNo, null, model);
        }

		this.addMessage(model, "采购订单" + purchaseNo + "入库成功");
		return returnView(purchaseNo, null, model);
	}

	/**
	 * 还原画面输入值
	 * 
	 * @param purchaseNo
	 *            采购订单号
	 * @param viewInputlist
	 *            画面采购入库输入值list
	 * @param model
	 *            画面model
	 * @return 画面id
	 */
	public String returnView(String purchaseNo,
			List<PmPurchaseStorage> viewInputlist, Model model) {

		PmPurchaseOrd returnPur = pmPurchaseStorageService
				.getPurchaseStorageInfo(purchaseNo);

		if (returnPur == null) {
			returnPur = new PmPurchaseOrd();
			returnPur.setPurchaseNo(purchaseNo);
		}

		// 入库excel按钮是否需要上传
		// 是否需要下载excel
		boolean isDownload = false;
		// 是否可以确定入库
		boolean isConfirm = false;
		for (PmPurchaseOrdDtl purDtl : returnPur.getPmPurchaseOrdDtlList()) {
			if (purDtl.getArrivalNum() > 0) {
				isConfirm = true;
				if (StringUtils.equals(purDtl.getIfSn(), CommonConstants.YES)) {
					isDownload = true;
					break;
				}
			}
		}

		if (ListUtils.size(viewInputlist) > 0) {
			// 将画面输入值list转换为map
			Map<String, PmPurchaseStorage> inputMap = ListUtils
					.convertListToMap(viewInputlist, "id");

			// 将画面输入值还原到采购订单明细
			for (PmPurchaseOrdDtl purDtl : returnPur.getPmPurchaseOrdDtlList()) {
				// 采购订单明细对应的画面输入值取得
				PmPurchaseStorage viewInput = inputMap.get(purDtl.getId());
				// 不存在，忽略
				if (viewInput == null) {
					continue;
				}

				purDtl.setArrivalNum(viewInput.getArrivalNum() == null ? 0
						: viewInput.getArrivalNum());
				purDtl.setWarehouse(viewInput.getWarehouse());
				purDtl.setEntryDate(viewInput.getEntryDate());
			}
		}

		model.addAttribute("isDownload", isDownload);
		model.addAttribute("isConfirm", isConfirm);
		model.addAttribute("storagePur", returnPur);

		return "inbody/pm/pm004";
	}
}