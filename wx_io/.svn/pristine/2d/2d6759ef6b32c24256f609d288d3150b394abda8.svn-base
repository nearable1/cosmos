package com.nssol_sh.controller.wx.view.cmn.wkf.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nssol_sh.entity.wx.view.common.wkf.process.ProcApiParamsModel;
import com.nssol_sh.service.wx.view.cmn.wkf.process.ApproveService;

/**
 * 案件审批
 */
@Controller
public class ApproveController {

	@Autowired
	private ApproveService service;

	/**
	 * 审批
	 * 
	 * @param
	 * @return 处理结果
	 */
	@RequestMapping(value = "/wx/view/cmn/wkf/process/approve", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String approve(@RequestParam(value = "usercd", required = true) String usercd,
			@RequestParam(value = "tenantid", required = true) String tenantid,
			@RequestParam(value = "aucd", required = false) String aucd,
			@RequestParam(value = "eucd", required = false) String eucd,
			@RequestParam(value = "dcd", required = true) String dcd,
			@RequestParam(value = "udid", required = false) String udid,
			@RequestParam(value = "smid", required = true) String smid,
			@RequestParam(value = "nodeid", required = true) String nodeid,
			@RequestParam(value = "flowid", required = true) String flowid,
			@RequestParam(value = "accountingFlag", required = false) String accountingFlag,
			@RequestParam(value = "comment", required = false) String comment,
			@RequestParam(value = "sf", required = false) String sf,
			@RequestParam(value = "data", required = false) String data) {

		// 请求参数赋值
		ProcApiParamsModel procApiParamsModel = new ProcApiParamsModel();

		// 赋值

		// 用户CD
		procApiParamsModel.usercd = usercd;

		// TenantID
		procApiParamsModel.tenantid = tenantid;

		// 处理权限人编码
		procApiParamsModel.aucd = aucd;

		// 处理执行人编码
		procApiParamsModel.eucd = eucd;

		// 处理部门编码
		procApiParamsModel.dcd = dcd;

		// 用户数据代码
		procApiParamsModel.udid = udid;

		// 系统案件号
		procApiParamsModel.smid = smid;

		// 节点代码
		procApiParamsModel.nodeid = nodeid;

		// 流程id
		procApiParamsModel.flowid = flowid;

		// 备注
		procApiParamsModel.comment = comment;

		// 服务器类型
		procApiParamsModel.sf = sf;

		// 是否有财务支付
		procApiParamsModel.accountingFlag = accountingFlag;

		// 用户数据
		procApiParamsModel.data = data;

		// 调用API
		return service.approve(procApiParamsModel);
	}

}
