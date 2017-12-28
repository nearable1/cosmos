package com.nssol_sh.controller.wx.view.ysz.yexp.exp1201;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nssol_sh.entity.wx.view.common.approveview.RequestCheckResultModel;
import com.nssol_sh.entity.wx.view.ysz.yexp.exp1201.Exp1201ApproveViewModel;
import com.nssol_sh.service.wx.view.common.route.RouteAnaliyzerService;
import com.nssol_sh.service.wx.view.ysz.yexp.exp1201.ApproveviewService;
import com.nssol_sh.util.constants.ProgramConstant;
import com.nssol_sh.util.tools.http.ResponseUtil;

@Controller("wx.view.ysz.yexp.exp1201.controller")
@RequestMapping("/wx/view/ysz/yexp/exp1201")
public class ApproveViewController {

	/**
	 * 跳转页面
	 */
	public static final String TURN_TO_PAGE = "/wx/ysz/yexp/exp1201/approveview";

	/**
	 * API错误页面
	 */
	private static final String API_ERROR_PAGE = "/wx/common/workflow/error/im_api_error";

	/**
	 * 逻辑对象
	 */
	@Autowired
	private ApproveviewService _service;

	/**
	 * URL获取类
	 */
	@Autowired
	private RouteAnaliyzerService _routeAnaliyzerService;

	/**
	 * 员工借款
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/approveview", method = RequestMethod.GET)
	public String approveView(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 检查请求对象的有效性
		RequestCheckResultModel rcrm = this._service.checkRequestAndReturn(request, response);

		// 如果检查不通过
		if (rcrm.error) {
			// 返回404
			return ResponseUtil.returnErrorNotFound(response);
		}

		// 调用审批数据获得API的请求体获得
		String postBody = _service.paramToApiPostBody(rcrm.rpm);

		// 请求路径
		String strUri = _routeAnaliyzerService.getApproveViewURL(ProgramConstant.PG_ID_YEXP_1201, rcrm.rpm.sf,
				rcrm.rpm.tenantid);

		// 调用API
		Exp1201ApproveViewModel result = _service.getData(postBody, strUri);

		// 如果有错
		if (result.error) {
			// 向JSP传递对象
			request.setAttribute("pageData", result);

			// 错误
			return API_ERROR_PAGE;
		}

		// 当前用户CD赋值
		result.accountInfo.userCd = rcrm.rpm.usercd;

		// 当前用户TenantID
		result.accountInfo.tenantId = rcrm.rpm.tenantid;

		// 服务器类型
		result.sf = rcrm.rpm.sf;

		// nc_token
		result.nc_token = rcrm.rpm.nc_token;

		// 向JSP传递对象
		request.setAttribute("pageData", result);

		return TURN_TO_PAGE;
	}
}
