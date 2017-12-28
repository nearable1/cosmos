package com.nssol_sh.controller.wx.view.cmn.wkf.user;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.RequestCheckResultModel;
import com.nssol_sh.entity.wx.view.common.wkf.user.list.UnprocessViewModel;
import com.nssol_sh.service.wx.view.common.list.UnpressListService;
import com.nssol_sh.service.wx.view.common.route.RouteAnaliyzerService;
import com.nssol_sh.util.constants.ProgramConstant;
import com.nssol_sh.util.tools.http.ResponseUtil;

/**
 * 未处理一览
 * 
 */
@Controller("wx.view.cmn.wkf.user.unprocesslist.controller")
@RequestMapping("/wx/view/cmn/wkf/user/unprocesslist")
public class UnprocessListController {

	/**
	 * 跳转页面
	 */
	public static final String TURN_TO_PAGE = "/wx/common/workflow/user/unprocesslist";

	/**
	 * 未处理一览取得逻辑
	 */
	@Autowired
	private UnpressListService _service;

	/**
	 * URL获取类
	 */
	@Autowired
	private RouteAnaliyzerService _routeAnaliyzerService;

	/**
	 * 每页默认行数
	 */
	@Value("${wx.io.ychips.workflow.list.pagerows}")
	private int _pageRows;

	/**
	 * 未处理一览跳转
	 * 
	 * @return 跳转
	 * @throws Exception
	 *             例外
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 检查请求对象的有效性
		RequestCheckResultModel rcrm = this._service.checkRequestAndReturn(request, response);

		// 如果检查不通过
		if (rcrm.error) {
			// 返回404
			return ResponseUtil.returnErrorNotFound(response);
		}

		// 设定初始值

		// 起始行
		rcrm.rpm.offset = String.valueOf(0);

		// 每页行数
		rcrm.rpm.count = String.valueOf(_pageRows);

		// 调用审批数据获得API的请求体获得
		String postBody = _service.paramToApiPostBody(rcrm.rpm);

		// 请求路径
		String strUri = _routeAnaliyzerService.getWkfProcessURL(ProgramConstant.WKF_CMN_ID_LIST_UNPROCESS, rcrm.rpm.sf);

		// 调用API
		UnprocessViewModel result = _service.getListData(postBody, strUri);

		// 如果有错
		if (result.error) {
			// 返回404
			return ResponseUtil.returnErrorNotFound(response);
		}

		// 每页行数
		result.pageRows = this._pageRows;

		// 起始行数
		result.offset = result.count;

		// 用户代码
		result.accountInfo.userCd = rcrm.rpm.usercd;

		// 服务器类型
		result.sf = rcrm.rpm.sf;

		result.nc_token = rcrm.rpm.nc_token;

		// TenantID
		result.accountInfo.tenantId = rcrm.rpm.tenantid;

		// 向JSP传递对象
		request.setAttribute("pageData", result);

		// 调用API
		return TURN_TO_PAGE;
	}

	/**
	 * 未处理一览翻页
	 * 
	 * @return 未处理一览数据
	 * @throws Exception
	 *             例外
	 */
	@RequestMapping(value = "/paging", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String paging(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 检查请求对象的有效性
		RequestCheckResultModel rcrm = this._service.checkRequestAndReturn(request, response);

		// 如果检查不通过
		if (rcrm.error) {
			// 返回404
			return JSON.toJSONString(new HashMap<String, String>());
		}

		// 调用审批数据获得API的请求体获得
		String postBody = _service.paramToApiPostBody(rcrm.rpm);

		// 请求路径
		String strUri = _routeAnaliyzerService.getWkfProcessURL(ProgramConstant.WKF_CMN_ID_LIST_UNPROCESS, rcrm.rpm.sf);

		// 调用API
		UnprocessViewModel result = _service.getListData(postBody, strUri);

		// 如果有值
		if (result != null) {
			return JSON.toJSONString(result);
		}

		// 如果无值
		return JSON.toJSONString(new HashMap<String, String>());
	}
}
