package com.inbody.crm.cm.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inbody.crm.cm.entity.CmAgreement;
import com.inbody.crm.cm.entity.CustomerManagement;
import com.inbody.crm.cm.entity.CustomerManagementExecl;
import com.inbody.crm.cm.service.CustomerManagementService;
import com.inbody.crm.common.entity.CoAttachments;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.BaseService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.common.utils.excel.ExportExcel;
import com.inbody.crm.common.web.BaseController;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;


@Controller
@RequestMapping(value = "${adminPath}/cm/cm001")
public class CustomerManagementController extends BaseController {


	@Autowired
	private CustomerManagementService customerManagementService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private AttachmentsService attachmentsService;
	
	@RequestMapping(value = {"list", ""})
	public String listData(CustomerManagement customer,HttpServletRequest request,HttpServletResponse response,Model model){
		if (customer.getIfEffective() == null) {
			customer.setIfEffective(1);
		}

		User user = UserUtils.getUser();
		Map<String, String> sqlMap = new HashMap<String, String>();
		sqlMap.put("dataScope", BaseService.dataScopeFilter(user));
		customer.setSqlMap(sqlMap);

		Page<CustomerManagement> page = customerManagementService.findPage(new Page<CustomerManagement>(request, response), customer); 
		model.addAttribute("page", page);
		return "nssol_bps/sd/cm/cm001";
	}
		
    @RequestMapping(value = "export", method=RequestMethod.GET)
    public String exportFile(CustomerManagement cm,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
    	try {

    		User user = UserUtils.getUser();
    		Map<String, String> sqlMap = new HashMap<String, String>();
    		sqlMap.put("dataScope", BaseService.dataScopeFilter(user));
    		cm.setSqlMap(sqlMap);
 
            String fileName = "主数据导出"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            List<CustomerManagement> cursomer = customerManagementService.findAllParentList(cm);
    		new ExportExcel("主数据导出", CustomerManagementExecl.class).setDataList(cursomer).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出主数据失败！失败信息："+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/cm/cm001?repage";
    }
    
    @RequestMapping(value = "getParentCo")
    @ResponseBody
    public Map<String, List<CustomerManagement>> searchParentCo(){
    	Map<String,List<CustomerManagement>> map = new HashMap<String, List<CustomerManagement>>();
    	List<CustomerManagement> list = customerManagementService.customerList();
    	map.put("list", list);
    	return map;
    }
    
    @RequestMapping(value = "getOffice/{id}")
    @ResponseBody
    public Map<String,CustomerManagement> getOffice(@PathVariable String id){
    	Map<String,CustomerManagement> map = new HashMap<String,CustomerManagement>();
    	CustomerManagement office = customerManagementService.getOffice(id);
    	map.put("office", office);
    	return map;
    }
    
    
    @RequestMapping(value = "newAndEdit")
    public String editCustomer(CustomerManagement customer,Model model){
    	CustomerManagement cm = null;
    	List<CmAgreement> cmAgreementList = null;
    	List<CoAttachments> coAttachmentslist = null;
    	if(null != customer.getCustomerId() && !"".equals(customer.getCustomerId())){
    		 List<CustomerManagement> list  = customerManagementService.editCustomerManagement(customer);
    		 if(null != list && list.size()>0){
    			 cm = list.get(0);
    		 }
    		 cmAgreementList = customerManagementService.cmAgreementList(customer.getCustomerId());
    		 coAttachmentslist = attachmentsService.getAttachmentList(customer.getCustomerId());
    	}else{
    		cm = customerManagementService.getOffice(UserUtils.getUser().getId());
    		if(null == cm){
    			cm = new CustomerManagement();
    			cm.setDevelopDate(new Date());
    		}
    		cm.setResponsiblePersonId(UserUtils.getUser().getId());
    		if(customer.getIfFromBusiness() != null && "1".equals(customer.getIfFromBusiness())){
        		cm.setCustomerType(customer.getCustomerType());
        		cm.setIfFromBusiness(customer.getIfFromBusiness());
        	}
    	}

    	cm.setCmAgreementList(cmAgreementList);
    	model.addAttribute("customerManagement", cm);
    	model.addAttribute("coAttachmentslist", coAttachmentslist);
//    	model.addAttribute("cmAgreementList", cmAgreementList);
    	return "nssol_bps/sd/cm/cm002";
    }
    
    @RequiresPermissions(value = {"bps:cm001:edit", "bps:cm001:editManager"}, logical = Logical.OR)
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> save(CustomerManagement customer,
			HttpServletRequest request, HttpServletResponse response){
    	
    	Map<String, Object> jsonMap = new HashMap<String, Object>();

		String message = customerManagementService.saveValidator(customer);

		if (StringUtils.isEmptyNull(message)) {
			
			customerManagementService.saveInfo(customer);

			jsonMap.put("success", true);
			jsonMap.put("message", null);
		} else {
			jsonMap.put("success", false);
			jsonMap.put("message", message);
		}
		return jsonMap;
    	
//    	Map<String,String> map = new HashMap<String, String>();
//    	String[] validityDateFrom = request.getParameterValues("validityDateFrom1");
//    	String[] validityDateTo = request.getParameterValues("validityDateTo1");
//    	String[] newRemarks = request.getParameterValues("newRemarks");
//    	CmAgreement cm = null;
//    	List<CmAgreement> cmAgreementList = new ArrayList<CmAgreement>();
//    	String customerId = "";
//    	CustomerManagement cmManagement  = null;
//    	List<CustomerManagement> customerList = new ArrayList<CustomerManagement>();
//    	if(null != customer.getCustomerId() && !"".equals(customer.getCustomerId())){
    		/*cmManagement = new CustomerManagement();
    		cmManagement.setCustomerChName(customer.getCustomerChName());
    		customerList = customerManagementService.editCustomerManagement(cmManagement);
    		if(null != customerList && customerList.size()>1){
    			map.put("message", "客户名称已存在,请确认后填写");
    			return map;
    		}*/
//    		customerManagementService.deleteAgreementAndDtlAll(customer.getCustomerId());
//    	}else{
//    		customerId = "KH" + commonService.getNextSequence("KH", 6);
//        	customer.setCustomerId(customerId);
        	/*cmManagement = new CustomerManagement();
    		cmManagement.setCustomerChName(customer.getCustomerChName());
    		customerList = customerManagementService.editCustomerManagement(cmManagement);
    		if(null != customerList && customerList.size()>=1){
    			map.put("message", "客户名称已存在,请确认后填写");
    			return map;
    		}*/
//    	}
//    	if((null != validityDateFrom && validityDateFrom.length>0) && (null != validityDateTo && validityDateTo.length>0)){
//    	if(customer.getCmAgreementList() != null && customer.getCmAgreementList().size() > 0){
//    		for(int i=0;i<validityDateFrom.length;i++){
//    		for(CmAgreement cmAgreement : customer.getCmAgreementList()){
//    			int isOneYear = DateUtils.isOneYear(validityDateFrom[i], validityDateTo[i]);
//    			int isOneYear = DateUtils.isOneYear(cmAgreement.getValidityDateFrom(), cmAgreement.getValidityDateTo());
//    			if (isOneYear < -1) {
//    				map.put("message", "协议的开始日期大于结束日期,请确认后填写");
//    				return map;
//    			} else if (isOneYear > 0) {
//    				map.put("message", "协议的日期区间大于一年,请确认后填写");
//    				return map;
//    			}
//        		cm = new CmAgreement();
//        		cm.setUpdateDate(new Date());
//        		cm.setDelFlag("0");
//        		cm.setCreateDate(new Date());
//        		cm.setCreateId(UserUtils.getUser().getId());
//        		cm.setAgreementId(customer.getCustomerId());
//        		cm.setUpdateId(UserUtils.getUser().getId());
//        		cm.setValidityDateFrom(cmAgreement.getValidityDateFrom());
//        		cm.setValidityDateTo(cmAgreement.getValidityDateTo());
//        		cm.setNewRemarks(cmAgreement.getNewRemarks());
//        		cm.preInsert();
//        		cmAgreementList.add(cm);
//        	}
//    	}
    	
//    	customerManagementService.saveCustomerAndAgreement(customer,cmAgreementList);
//    	return map;
    }

    @RequiresPermissions(value = {"bps:cm001:edit", "bps:cm001:editManager"}, logical = Logical.OR)
    @RequestMapping(value = "delete/file/{fileId}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable String fileId) {
        String delFileName = attachmentsService.deleteFile(fileId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", "文件：" + delFileName + "删除成功！");
        resultMap.put("success", new Boolean(true));
        return resultMap;
    }
    
    @RequestMapping(value = "download/file/{fileId}")
    public void downLoadFile(@PathVariable String fileId, HttpServletRequest request,
            HttpServletResponse response) {
        attachmentsService.downloadFile(fileId, request, response);
    }
    
    @RequiresPermissions(value = {"bps:cm001:edit", "bps:cm001:editManager"}, logical = Logical.OR)
    @RequestMapping(value = "deleteAgreement/{id}")
    @ResponseBody
    public Map<String, String> deleteAgreementAndDtl(@PathVariable String id) {
        return customerManagementService.deleteAgreementAndDtlById(id);
    }
}
