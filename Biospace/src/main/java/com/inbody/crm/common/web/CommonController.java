/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.common.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.entity.EmployeeSearch;
import com.inbody.crm.common.entity.SelectEntity;
import com.inbody.crm.common.entity.SmPackageInfoEntity;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * CommonController
 * @author yangj
 * @version 2017-08-20
 */
@Controller
@RequestMapping(value = "${adminPath}/common")
public class CommonController extends BaseController {

	@Autowired
	private CommonService commonService;

    /**
     * 模糊查询物料信息
     */
    @RequestMapping(value = "materials", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMaterialInfos(String keyword,
            @RequestParam(required = false) String type, @RequestParam Integer pageNum,
            @RequestParam Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtils.isEmptyNull(keyword)) {
			return jsonMap;
		}

        Page<SelectEntity> page = new Page<SelectEntity>(request, response);
        page.setPageNo(pageNum);
        page.setPageSize(pageSize);

        page = commonService.getMaterialDictList(page,
                Encodes.urlDecode(keyword), type);
        jsonMap.put("materials", page.getList());
        jsonMap.put("totalCount", page.getCount());
        return jsonMap;
    }

	/**
	 * 根据物料号取得物料信息
	 */
	@RequestMapping(value = "materials/{materialNo}", method = RequestMethod.GET)
	public @ResponseBody SelectEntity getMaterialInfoByNo(
			@PathVariable String materialNo) {

		SelectEntity material = commonService.getMaterialInfoByNo(materialNo);

		return material;
	}

	/**
	 * 模糊查询客户信息
	 */
	@RequestMapping(value = "customers", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getCustomerInfos(String keyword,
			@RequestParam(required = false) String type) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtils.isEmptyNull(keyword)) {
			return jsonMap;
		}
		
		String pType = type;
//		if (!StringUtils.isEmptyNull(type)) {
//
//			String[] types = type.split(",");
//			for (int i = 0 ; i <types.length ; i++ ) {
//				
//			}
//		}
		if (StringUtils.isEmptyNull(keyword)) {
			return jsonMap;
		}

		List<SelectEntity> sustomers = commonService.getCustomerDictList(
				Encodes.urlDecode(keyword), pType);

		jsonMap.put("customers", sustomers);
		return jsonMap;
	}

	/**
	 * 根据客户ID取得客户信息
	 */
	@RequestMapping(value = "customers/{customerId}", method = RequestMethod.GET)
	public @ResponseBody SelectEntity getCustomerInfoById(
			@PathVariable String customerId) {

		SelectEntity sustomer = commonService.getCustomerInfoByNo(customerId);

		return sustomer;
	}

	/**
	 * 取得套餐物料信息
	 */
	@RequestMapping(value = "smPackageInfos", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSmPackageInfoByMaterialNo(
			@RequestParam(required = false) String materialNo) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		List<SmPackageInfoEntity> smPackageInfos = commonService.getSmPackageInfoByMaterialNo(materialNo);

		jsonMap.put("smPackageInfos", smPackageInfos);
		return jsonMap;
	}
	
	/**
	 * 组织信息取得
	 * @param employeeId
	 *        人
	 * @return 组信息
	 */
	@RequestMapping(value = "getOrganize", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getOrganize(@RequestParam String employeeId) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String organize = commonService.getOrganize(employeeId);

		jsonMap.put("organize", organize);
		return jsonMap;
	}
	
	/**
	 * 标准价格取得
	 * @param priceSystem
	 *        价格体系
	 * @param customerId
	 *        客户
	 * @param materialNo
	 *        物料号
	 * @param region
	 *        地区
	 * @param industry
	 *        行业
	 * @return 标准价格
	 */
	@RequestMapping(value = "getStandardPrice", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getStandardPrice(@RequestParam(required = false) String priceSystem,
			@RequestParam(required = false) String customerId,
			@RequestParam(required = false) String materialNo,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String industry) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();

		String standardPrice = commonService.getStandardPrice(priceSystem, customerId, materialNo, region, industry);

		jsonMap.put("standardPrice", standardPrice);
		return jsonMap;
	}

	/**
	 * 模糊查询人员信息
	 */
	@RequestMapping(value = "employees", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getEmployeeInfos(String keyword,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String shiro) {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtils.isEmptyNull(keyword)) {
			return jsonMap;
		}

		EmployeeSearch employeeSearch = new EmployeeSearch();
		employeeSearch.setKey(Encodes.urlDecode(keyword));
		employeeSearch.setType(type);
		if (StringUtils.equals(shiro, CommonConstants.YES)) {

			User user = UserUtils.getUser();
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("dataScope", BaseService.dataScopeFilter(user, "rm:repair:view"));
			employeeSearch.setSqlMap(sqlMap);
		}

		List<SelectEntity> employees = commonService.getEmployeeDictList(employeeSearch);

		jsonMap.put("employees", employees);
		return jsonMap;
	}

	/**
	 * 根据员工ID取得已删除的员工信息
	 */
	@RequestMapping(value = "employees/{employeeId}", method = RequestMethod.GET)
	public @ResponseBody SelectEntity getEmployeeInfoById(
			@PathVariable String employeeId) {

		SelectEntity employeeInfo = commonService.getEmployeeInfoById(employeeId);

		return employeeInfo;
	}

}