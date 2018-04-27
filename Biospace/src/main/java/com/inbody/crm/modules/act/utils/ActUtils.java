/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.inbody.crm.modules.act.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inbody.crm.common.annotation.FieldName;
import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.ObjectUtils;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.dao.ActDao;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.entity.User;

/**
 * 流程工具
 * @author ThinkGem
 * @version 2013-11-03
 */
public class ActUtils {

//	private static Logger logger = LoggerFactory.getLogger(ActUtils.class);
	
	/**
	 * 定义流程定义KEY，必须以“PD_”开头
	 * 组成结构：string[]{"流程标识","业务主表表名"}
	 */
	public static final String[] PD_LEAVE = new String[]{"leave", "oa_leave"};
	public static final String[] PD_TEST_AUDIT = new String[]{"test_audit", "oa_test_audit"};
	
	/**
	 * 拜访申请流程
	 */
	public static final String[] VISITING_AUDIT = new String[]{"visiting", " vr_visit"};
	
	/**
	 * 拜访报告申请流程
	 */
	public static final String[] VISITING_REPORT_AUDIT = new String[]{"visiting_report", " vr_visit"};
	
	/**
	 * 价格申请流程
	 */
	public static final String[] PD_PRICE_APPLY = new String[]{"ps_apply", "PS_APPLY_PRICE"};

    /**
     * 机器采购订单申请流程
     */
    public static final String[] PD_MC_PURCHASE = new String[] { "mc_purchase",
            "pm_purchase_ord" };

    /**
     * 配件采购订单申请流程
     */
    public static final String[] PD_AC_PURCHASE = new String[] { "ac_purchase",
            "pm_purchase_ord" };
    
    /**
     * 销售员工评价申请流程
     */
    public static final String[] ACT_EE_SALE_EMP = new String[] { "ee_sale_emp_flow",
            "ee_sale_emp" };
    
    /**
     * 非销售销售员工评价申请流程
     */
    public static final String[] ACT_EE_NON_SALE_EMP = new String[] { "ee_non_sale_emp_flow",
            "ee_nonsale_emp" };

	/**
	 * 合同申请流程
	 */
	public static final String[] PD_SO_ORDER = new String[] { "order",
			"so_order" };

	/**
	 * 其他入库归还申请流程
	 */
	public static final String[] SM_IN_SENDBACK = new String[] { "sm_in_sendback",
			"sm_storage_app" };

	/**
	 * 其他入库退货申请流程
	 */
	public static final String[] SM_IN_REFUND = new String[] { "sm_in_refund",
			"sm_storage_app" };

	/**
	 * 其他入库换货申请流程
	 */
	public static final String[] SM_IN_EXCHANGE = new String[] { "sm_in_exchange",
			"sm_storage_app" };

	/**
	 * 其他入库其他申请流程
	 */
	public static final String[] SM_IN_OTHER = new String[] { "sm_in_other",
			"sm_storage_app" };

	/**
	 * 发票申请流程
	 */
	public static final String[] PD_IM_INVOICE = new String[] { "invoice",
			"im_invoice" };

	/**
	 * 发货申请流程
	 */
	public static final String[] PD_SO_APPLY_DELIVER = new String[] { "apply_deliver",
			"so_apply_deliver" };

	/**
	 * 
	 * 其他出库
	 */
	public static final String [] SM_STORAGE = new String[] {"sm_out","SM_STORAGE_APP"};
	
    /**
     * 报价单发票申请流程
     */
    public static final String[] PD_QUOTA_INVOICE = new String[] { "quota_invoice",
            "im_invoice" };
	
		
	public static final String [] SM_SCRAP = new String[] {"sm_scrap","SM_STORAGE_APP"};

	public static final String [] SM_OTHIERT = new String[] {"sm_othert","SM_STORAGE_APP"};

	
	public static final String [] SM_CHANGE = new String[] {"sm_change","SM_STORAGE_APP"};

	public static final String [] SM_DELAY = new String[] {"sm_delay","SM_STORAGE_APP"};


	/**
	 * 配件采购订单申请流程
	 */
	public static final String[] PD_ACC_PURCHASE = new String[] {
			"acc_purchase", "pm_purchase_ord" };

	/**
	 * 招标授权申请流程
	 */
	public static final String[] TA_TENDER_AUTHOR = new String[] {
			"ta_tender_author", "TA_TENDER_AUTHOR" };
	
//	/**
//	 * 流程定义Map（自动初始化）
//	 */
//	private static Map<String, String> procDefMap = new HashMap<String, String>() {
//		private static final long serialVersionUID = 1L;
//		{
//			for (Field field : ActUtils.class.getFields()){
//				if(StringUtils.startsWith(field.getName(), "PD_")){
//					try{
//						String[] ss = (String[])field.get(null);
//						put(ss[0], ss[1]);
//					}catch (Exception e) {
//						logger.debug("load pd error: {}", field.getName());
//					}
//				}
//			}
//		}
//	};
//	
//	/**
//	 * 获取流程执行（办理）URL
//	 * @param procId
//	 * @return
//	 */
//	public static String getProcExeUrl(String procId) {
//		String url = procDefMap.get(StringUtils.split(procId, ":")[0]);
//		if (StringUtils.isBlank(url)){
//			return "404";
//		}
//		return url;
//	}
	
	@SuppressWarnings({ "unused" })
	public static Map<String, Object> getMobileEntity(Object entity,String spiltType){
		if(spiltType==null){
			spiltType="@";
		}
		Map<String, Object> map = Maps.newHashMap();

		List<String> field = Lists.newArrayList();
		List<String> value = Lists.newArrayList();
		List<String> chinesName =Lists.newArrayList();
		
		try{
			for (Method m : entity.getClass().getMethods()){
				if (m.getAnnotation(JsonIgnore.class) == null && m.getAnnotation(JsonBackReference.class) == null && m.getName().startsWith("get")){
					if (m.isAnnotationPresent(FieldName.class)) {
						Annotation p = m.getAnnotation(FieldName.class);
						FieldName fieldName=(FieldName) p;
						chinesName.add(fieldName.value());
					}else{
						chinesName.add("");
					}
					if (m.getName().equals("getAct")){
						Object act = m.invoke(entity, new Object[]{});
						Method actMet = act.getClass().getMethod("getTaskId");
						map.put("taskId", ObjectUtils.toString(m.invoke(act, new Object[]{}), ""));
					}else{
						field.add(StringUtils.uncapitalize(m.getName().substring(3)));
						value.add(ObjectUtils.toString(m.invoke(entity, new Object[]{}), ""));
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("beanTitles", StringUtils.join(field, spiltType));
		map.put("beanInfos", StringUtils.join(value, spiltType));
		map.put("chineseNames", StringUtils.join(chinesName, spiltType));
		
		return map;
	}
	
	/**
	 * 获取流程表单URL
	 * @param formKey
	 * @param act 表单传递参数
	 * @return
	 */
	public static String getFormUrl(String formKey, Act act){
		
		StringBuilder formUrl = new StringBuilder();
		
		String formServerUrl = Global.getConfig("activiti.form.server.url");
		if (StringUtils.isBlank(formServerUrl)){
			formUrl.append(Global.getAdminPath());
		}else{
			formUrl.append(formServerUrl);
		}
		
		formUrl.append(formKey).append(formUrl.indexOf("?") == -1 ? "?" : "&");
		formUrl.append("act.taskId=").append(act.getTaskId() != null ? act.getTaskId() : "");
		formUrl.append("&act.taskName=").append(act.getTaskName() != null ? Encodes.urlEncode(act.getTaskName()) : "");
		formUrl.append("&act.taskDefKey=").append(act.getTaskDefKey() != null ? act.getTaskDefKey() : "");
		formUrl.append("&act.procInsId=").append(act.getProcInsId() != null ? act.getProcInsId() : "");
		formUrl.append("&act.procDefId=").append(act.getProcDefId() != null ? act.getProcDefId() : "");
		formUrl.append("&act.status=").append(act.getStatus() != null ? act.getStatus() : "");
        formUrl.append("&act.businessId=").append(act.getBusinessId() != null ? act.getBusinessId() : "");
        // 主业务id是否为空
        if (StringUtils.isBlank(act.getMainBusinessId())) {
            // 主业务id为空，则id为工作流业务id
            formUrl.append("&id=")
                    .append(act.getBusinessId() != null ? act.getBusinessId() : "");
        } else {
            // 主业务id不为空，则id为主业务id
            formUrl.append("&id=").append(act.getMainBusinessId());
        }

		return formUrl.toString();
	}
	
	/**
	 * 转换流程节点类型为中文说明
	 * @param type 英文名称
	 * @return 翻译后的中文名称
	 */
	public static String parseToZhType(String type) {
		Map<String, String> types = new HashMap<String, String>();
		types.put("userTask", "用户任务");
		types.put("serviceTask", "系统任务");
		types.put("startEvent", "开始节点");
		types.put("endEvent", "结束节点");
		types.put("exclusiveGateway", "条件判断节点(系统自动根据条件处理)");
		types.put("inclusiveGateway", "并行处理任务");
		types.put("callActivity", "子流程");
		return types.get(type) == null ? type : types.get(type);
	}

	public static UserEntity toActivitiUser(User user){
		if (user == null){
			return null;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setId(user.getLoginName());
		userEntity.setFirstName(user.getName());
		userEntity.setLastName(StringUtils.EMPTY);
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}
	
	public static GroupEntity toActivitiGroup(Role role){
		if (role == null){
			return null;
		}
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setId(role.getEnname());
		groupEntity.setName(role.getName());
		groupEntity.setType(role.getRoleType());
		groupEntity.setRevision(1);
		return groupEntity;
	}
	

	/**
	 * 更新工作流业务主表workflowStatus字段状态
	 * 
	 * @param pdkey          流程定义常量
	 * @param workflowStatus 要更新的流程状态
	 * @param businessId     要更新的业务主表记录id
	 * @return  更新结果
	 */
	public static int updateWorkflowStatus(String[] pdkey,
			String workflowStatus, String businessId) {

		Act act = new Act();
		act.setBusinessTable(pdkey[1]);
		act.setBusinessId(businessId);
		act.setStatus(workflowStatus);

		return SpringContextHolder.getBean(ActDao.class)
				.updateWorkFlowStatusByBusinessId(act);
	}
	
	public static void main(String[] args) {
		 User user = new User();
		 System.out.println(getMobileEntity(user, "@"));
	}
}
