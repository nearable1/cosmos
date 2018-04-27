package com.inbody.crm.cm.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.inbody.crm.cm.dao.CustomerManagementDao;
import com.inbody.crm.cm.entity.CmAgreement;
import com.inbody.crm.cm.entity.CmAgreementDtl;
import com.inbody.crm.cm.entity.CustomerManagement;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.common.service.AttachmentsService;
import com.inbody.crm.common.service.CommonService;
import com.inbody.crm.common.service.CrudService;
import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;


@Service
@Transactional(readOnly = true)
public class CustomerManagementService extends CrudService<CustomerManagementDao, CustomerManagement>{
	
	@Autowired
	private CustomerManagementDao  customerManagementDao;
	
	@Autowired
	private AttachmentsService attachmentsService;
	
	@Autowired
	private CommonService commonService;
	
	public Page<CustomerManagement> findPage(Page<CustomerManagement> page, CustomerManagement customer) {
		return super.findPage(page, customer);
	}
	
	public List<CustomerManagement> findAllParentList(CustomerManagement customerManagement){
		return dao.findAllList(customerManagement);
	}

	public List<CustomerManagement> editCustomerManagement(CustomerManagement customer){
		return customerManagementDao.selectCustomerById(customer);
	}
	
	public List<CmAgreement> cmAgreementList(String customerId){
		return customerManagementDao.selectAgreement(customerId);
	}
	
	public List<CustomerManagement> customerList(){
		return customerManagementDao.selectCustomerParent();
	}
	
	public CustomerManagement getOffice(String id){
		return customerManagementDao.selectOfficeById(id);
	}
	
	@Transactional(readOnly = false)
	public void saveCustomerAndAgreement(CustomerManagement customer,List<CmAgreement> list){
//		Map<String,String> map = new HashMap<String, String>();
		List<CmAgreementDtl> dtlList = new ArrayList<CmAgreementDtl>();
		try {
			customer.preInsert();
			customer.setDelFlag("0");
			customer.setCreateId(UserUtils.getUser().getId());
			customer.setUpdateId(UserUtils.getUser().getId());
			customerManagementDao.saveOrUpdate(customer);
			if(null != list && list.size()>0){
				customerManagementDao.insertAgreement(list);
				dtlList = compareDate(list,customer.getCustomerId());
				if(null != dtlList && dtlList.size()>0){
					customerManagementDao.insertAgreementDtl(dtlList);
				}
			}
			this.saveAttachmentsFile(customer.getCustomerId(), customer.getFiles());
//			map.put("message","修改成功");
		} catch (Exception e) {
			e.printStackTrace();
//			map.put("message","修改失败:"+e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void saveInfo(CustomerManagement customer){

    	CmAgreement cm = null;
    	List<CmAgreement> cmAgreementList = new ArrayList<CmAgreement>();
    	String customerId = "";
    	
    	if(null != customer.getCustomerId() && !"".equals(customer.getCustomerId())){
    		this.deleteAgreementAndDtlAll(customer.getCustomerId());
    	}else{
    		customerId = "KH" + commonService.getNextSequence("KH", 6);
        	customer.setCustomerId(customerId);
    	}
    	
    	if(customer.getCmAgreementList() != null && customer.getCmAgreementList().size() > 0){
    		for(CmAgreement cmAgreement : customer.getCmAgreementList()){

        		cm = new CmAgreement();
        		cm.setUpdateDate(new Date());
        		cm.setDelFlag("0");
        		cm.setCreateDate(new Date());
        		cm.setCreateId(UserUtils.getUser().getId());
        		cm.setAgreementId(customer.getCustomerId());
        		cm.setUpdateId(UserUtils.getUser().getId());
        		cm.setValidityDateFrom(cmAgreement.getValidityDateFrom());
        		cm.setValidityDateTo(cmAgreement.getValidityDateTo());
        		cm.setNewRemarks(cmAgreement.getNewRemarks());
        		cm.preInsert();
        		cmAgreementList.add(cm);
    		}
    	}
    	
    	this.saveCustomerAndAgreement(customer,cmAgreementList);
	}
	
	public String saveValidator(CustomerManagement customer) {
		List<String> message = new ArrayList<String>();

//    	List<CustomerManagement> customerList = new ArrayList<CustomerManagement>();
		CustomerManagement cmManagement = new CustomerManagement();
		cmManagement.setCustomerChName(customer.getCustomerChName());
		List<CustomerManagement> customerList = this.editCustomerManagement(cmManagement);
    	if(null != customer.getCustomerId() && !"".equals(customer.getCustomerId())){
    		if((null != customerList && customerList.size()>0) && !StringUtils.equals(customerList.get(0).getCustomerId(), customer.getCustomerId())){
    			message.add("客户名称已存在,请确认后填写");
    		}
    	} else {
    		if(null != customerList && customerList.size()>0){
    			message.add("客户名称已存在,请确认后填写");
    		}
    	}

    	if(customer.getCmAgreementList() != null && customer.getCmAgreementList().size() > 0){
    		for(CmAgreement cmAgreement : customer.getCmAgreementList()){
    			int isOneYear = DateUtils.isOneYear(cmAgreement.getValidityDateFrom(), cmAgreement.getValidityDateTo());
    			if (isOneYear < -1) {
    				message.add("协议的开始日期大于结束日期,请确认后填写");
    			} else if (isOneYear > 0) {
    				message.add("协议的日期区间大于一年,请确认后填写");
    			}
        	}
    	}
		
		StringBuilder sbMessage = new StringBuilder();
		if (message.size() > 0) {
			for (String str : message) {
				int index = 1;
				
				if (index == message.size()) {
					sbMessage.append(str);
				} else {
					sbMessage.append(str).append("<br/>");
				}
				
				index++;
			}
		} else {
			return null;
		}
		
		return sbMessage.toString();
	}
	public List<CmAgreementDtl> compareDate(List<CmAgreement> list,String agreementId){
		
		Date date = null;
		Date date1 = null;
		CmAgreementDtl cmDtl  = null;
		User user = UserUtils.getUser();
		List<CmAgreementDtl> dtlList = new ArrayList<CmAgreementDtl>();
		int lineNo = 0;
		int period = 0;
		for(int i = 0;i<list.size();i++){
			lineNo = 0;
			period = 0;
			Calendar cal = Calendar.getInstance();
			Calendar cal1 = Calendar.getInstance();
			if(null != list.get(i).getValidityDateFrom() && !"".equals(list.get(i).getValidityDateFrom())){
				date = DateUtils.parse(list.get(i).getValidityDateFrom(), "yyyy-MM-dd");
			}
			if(null != list.get(i).getValidityDateTo() && !"".equals(list.get(i).getValidityDateTo())){
				date1 = DateUtils.parse(list.get(i).getValidityDateTo(), "yyyy-MM-dd");
			}
			
			cal.setTime(date);
			cal1.setTime(date1);

			while(true){
				lineNo ++;
				period++;
				cmDtl = new CmAgreementDtl();
				cmDtl.setLineNo(lineNo);
				cmDtl.setPeriod(Integer.toString(period));
				cmDtl.setDelFlag("0");
				cmDtl.setAgreementId(list.get(i).getId());
				cmDtl.setCreateDate(new Date());
				cmDtl.setUpdateDate(new Date());
				cmDtl.setUpdateId(user.getId());
				cmDtl.setCreateId(user.getId());
				cmDtl.preInsert();
				cmDtl.setPeriodDateFrom(cal.getTime());
				
				cal.add(Calendar.MONTH, 1);
				
				if (DateUtils.compareDate(cal.getTime(), cal1.getTime()) >= 0) {
					cmDtl.setPeriodDateTo(cal1.getTime());
					dtlList.add(cmDtl);
					break;
				} else {
					cal.add(Calendar.MONTH, -1);
					if (cal.get(Calendar.MONTH) == 1 && cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
						cal.add(Calendar.MONTH, 1);
						int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						cal.set(Calendar.DAY_OF_MONTH, lastDay);
					} else {
						cal.add(Calendar.MONTH, 1);
					}
					cal.add(Calendar.DAY_OF_MONTH, -1);
					cmDtl.setPeriodDateTo(cal.getTime());
					cal.add(Calendar.DAY_OF_MONTH, 1);
					dtlList.add(cmDtl);
				}
			}
//			if((DateUtils.compareDate(date1, date)) == 1){
//				int year = date1.getYear()  - date.getYear();
//				int month = (date1.getMonth()+1) - (date.getMonth()+1);
//				if(year >=1){
//					while(true){
//						lineNo++;
//						period++;
//						cmDtl = new CmAgreementDtl();
//						cmDtl.setAgreementId(list.get(i).getId());
//						cmDtl.setDelFlag("0");
//						cmDtl.setPeriod(Integer.toString(period));
//						cmDtl.setCreateDate(new Date());
//						cmDtl.setUpdateDate(new Date());
//						cmDtl.setUpdateId(user.getId());
//						cmDtl.setCreateId(user.getId());
//						cmDtl.setLineNo(lineNo);
//						cmDtl.preInsert();
//						cmDtl.setPeriodDateFrom(cal.getTime());
//						cal.add(Calendar.MONTH, 1);
//						if(cal.get(Calendar.YEAR) == cal1.get(Calendar.YEAR)){
//							if((cal.get(Calendar.MONTH)+1) >=(cal1.get(Calendar.MONTH)+1)){
//								cmDtl.setPeriodDateTo(date1);
//								cal.setTime(date1);
//							}else{
//								cal.add(Calendar.DAY_OF_MONTH, -1);
//								cmDtl.setPeriodDateTo(cal.getTime());
//								cal.add(Calendar.DAY_OF_MONTH, 1);
//							}
//						}else{
//							cal.add(Calendar.DAY_OF_MONTH, -1);
//							cmDtl.setPeriodDateTo(cal.getTime());
//							cal.add(Calendar.DAY_OF_MONTH, 1);
//						}
//						dtlList.add(cmDtl);
//						if (period == 12) {
//							period = 1;
//						}
//						if(DateUtils.compareDate(date1, cal.getTime()) == 0){
//							break;
//						}
//					}
//				}else{
//					if(month>=1){
//						while(true){
//							lineNo ++;
//							period++;
//							cmDtl = new CmAgreementDtl();
//							cmDtl.setLineNo(lineNo);
//							cmDtl.setPeriod(Integer.toString(period));
//							cmDtl.setDelFlag("0");
//							cmDtl.setAgreementId(list.get(i).getId());
//							cmDtl.setCreateDate(new Date());
//							cmDtl.setUpdateDate(new Date());
//							cmDtl.setUpdateId(user.getId());
//							cmDtl.setCreateId(user.getId());
//							cmDtl.preInsert();
//							cmDtl.setPeriodDateFrom(cal.getTime());
//							cal.add(Calendar.MONTH, 1);
//							if((cal.get(Calendar.MONTH)+1) == (cal1.get(Calendar.MONTH)+1)){
//								cmDtl.setPeriodDateTo(cal1.getTime());
//								cal.setTime(date1);
//							}else{
//								cal.add(Calendar.DAY_OF_MONTH, -1);
//								cmDtl.setPeriodDateTo(cal.getTime());
//								cal.add(Calendar.DAY_OF_MONTH, 1);
//							}
//							dtlList.add(cmDtl);
//							if (period == 12) {
//								period = 1;
//							}
//							if(DateUtils.compareDate(date1, cal.getTime()) == 0){
//								break;
//							}
//						}
//					}else{
//						cmDtl = new CmAgreementDtl();
//						cmDtl.setLineNo(1);
//						cmDtl.setPeriod("1");
//						cmDtl.setDelFlag("0");
//						cmDtl.setAgreementId(list.get(i).getId());
//						cmDtl.setCreateDate(new Date());
//						cmDtl.setUpdateDate(new Date());
//						cmDtl.setUpdateId(user.getId());
//						cmDtl.setCreateId(user.getId());
//						cmDtl.preInsert();
//						cmDtl.setPeriodDateFrom(cal.getTime());
//						cmDtl.setPeriodDateTo(cal1.getTime());
//						dtlList.add(cmDtl);
//					}
//				}
//			}
		}
		return dtlList;
	}
	
	@Transactional(readOnly = false)
    public void saveAttachmentsFile(String id, MultipartFile[] files) {
		 //保存附件信息
       if (files != null && files.length > 0) {
           attachmentsService.uploadFileList(id,
                   CommonConstants.ATTACHMENT_TYPE_STORAGE, files);
       }
    }
	
	
	@Transactional(readOnly = false)
    public Map<String,String> deleteAgreementAndDtlById(String id) {
		Map<String,String> map = new HashMap<String, String>();
		try {
			map.put("agreementId", id);
			map.put("customerId", "");
			int dtlNum = customerManagementDao.deleteAgreementDtl(map);
			int agreement = customerManagementDao.deleteAgreementBy(id);
			System.out.println(agreement+":"+dtlNum);
			map.put("message", "协议信息删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
		}
		return map;
    }
	
	@Transactional(readOnly = false)
    public void deleteAgreementAndDtlAll(String customerId) {
		Map<String,String> map = new HashMap<String, String>();
		try {
			map.put("agreementId", "");
			map.put("customerId", customerId);
			customerManagementDao.deleteAgreementDtl(map);
			customerManagementDao.deleteAgreement(customerId);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("message", e.getMessage());
		}
    }
}
