package com.inbody.crm.listener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.constants.TemplatePath;
import com.inbody.crm.common.utils.SendMail;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.dao.ActDao;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

public class CommonProcessEndListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	
	private ActDao actDao = SpringContextHolder.getBean(ActDao.class);
	
	private RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);

	private HistoryService historyService = SpringContextHolder.getBean(HistoryService.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String pbkey = execution.getProcessBusinessKey();
		String[] keys = pbkey.split(":");
		if(keys != null && keys.length >= 2){
			Act act = new Act();
			act.setBusinessTable(keys[0]);
			act.setBusinessId(keys[1]);
			act.setStatus(CommonConstants.WORKFLOW_STATUS_50);
			
			String procDefKey = execution.getProcessDefinitionId().split(":")[0];
			
			//拜访报告的工作流状态存放在status2中
			if(StringUtils.equals(procDefKey, "visiting_report")){
				actDao.updateWorkFlowStatus2ByBusinessId(act);
				//更新主数据表最终拜访日期
				actDao.updateCustomerVisitDateByBusinessId(act);
			}else if (StringUtils.equals(procDefKey, "invoice")){
				String orderNo = actDao.getOrderNoByBusinessId(act.getBusinessId());
				
				act.setBusinessId(orderNo);
				actDao.updateWorkFlowStatusByOrderNo(act);
			}else{
				actDao.updateWorkFlowStatusByBusinessId(act);
			}
		}

		String applyDate = null;
		String taskId = "";
		String taskName = "";
		String taskDefKey = "";
		String procInsId = execution.getProcessInstanceId();
		HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
		historicActivityInstanceQuery.processInstanceId(procInsId);
		historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
		historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
		List<HistoricActivityInstance> historicActivityInstanceQuerys = historicActivityInstanceQuery.list();
		
		if (historicActivityInstanceQuerys != null && historicActivityInstanceQuerys.size() > 0) {

			applyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(historicActivityInstanceQuerys.get(historicActivityInstanceQuerys.size()-1).getStartTime());
		}

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) 
				repositoryService.getProcessDefinition(execution.getProcessDefinitionId());

		String workflowName = processDefinition.getName();
		String applyUserLoginName = execution.getVariable("apply").toString();
		User userApply=UserUtils.getByLoginName(applyUserLoginName);
		String applyUserName = userApply.getName();
		
		String urlFormat = "{0}/act/task/form?taskId={1}&taskName={2}&taskDefKey={3}&procInsId={4}&procDefId={5}&status={6}";
		String ctx = Global.getHost() + Global.getAdminPath();

		String procDefId = execution.getProcessDefinitionId();
		String status = "finish";
		String url = MessageFormat.format(urlFormat, ctx, taskId, taskName, taskDefKey, procInsId, procDefId, status);

		String subject = "申请审批通知" + "_" + workflowName + "_" + applyUserName;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workflowName", workflowName);
		params.put("applyUserName", applyUserName);
		params.put("applyDate", applyDate);
		params.put("url", url);
		
//		SendMailUtil.sendFtlMail(subject, TemplatePath.NORMAL_APPOVER.value(), params, userApply.getEmail());
		new SendMail(subject, TemplatePath.NORMAL_APPOVER.value(), params, userApply.getEmail()).run();
	}

}
