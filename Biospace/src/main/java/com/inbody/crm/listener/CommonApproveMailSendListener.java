package com.inbody.crm.listener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import com.inbody.crm.common.config.Global;
import com.inbody.crm.common.constants.TemplatePath;
import com.inbody.crm.common.utils.Encodes;
import com.inbody.crm.common.utils.SendMail;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

public class CommonApproveMailSendListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	
	private RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);
	
	// add by zhanglulu 20170814 start
	private HistoryService historyService = SpringContextHolder.getBean(HistoryService.class);
	// add by zhanglulu 20170814 end
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String assignee = delegateTask.getAssignee();
		User user = UserUtils.getByLoginName(assignee);
		
		String proInsId = delegateTask.getProcessInstanceId();
		if(user != null){
			
			// add by zhanglulu 20170814 start
			String hisAssignee = null;
			String hisAssigneeName = null;
			String applyDate = null;
			HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
			historicActivityInstanceQuery.processInstanceId(proInsId);
			historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
			historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
			List<HistoricActivityInstance> historicActivityInstanceQuerys = historicActivityInstanceQuery.list();
			
			if (historicActivityInstanceQuerys != null && historicActivityInstanceQuerys.size() > 0) {

				hisAssignee = historicActivityInstanceQuerys.get(0).getAssignee();
				applyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(historicActivityInstanceQuerys.get(historicActivityInstanceQuerys.size()-1).getStartTime());
				if (!StringUtils.isEmptyNull(hisAssignee)) {
					hisAssigneeName = UserUtils.getByLoginName(hisAssignee).getName();
				}
			}

			if (!StringUtils.equals(assignee, hisAssignee) && !StringUtils.isEmptyNull(hisAssignee)) {
			// add by zhanglulu 20170814 end
				ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) 
						repositoryService.getProcessDefinition(delegateTask.getProcessDefinitionId());

				String workflowName = processDefinition.getName();
				String applyUserLoginName = delegateTask.getVariable("apply").toString();
				User userApply=UserUtils.getByLoginName(applyUserLoginName);
				String applyUserName = userApply.getName();
				
				String urlFormat = "{0}/act/task/form?taskId={1}&taskName={2}&taskDefKey={3}&procInsId={4}&procDefId={5}&status={6}";
				String ctx = Global.getHost() + Global.getAdminPath();
				String taskId = delegateTask.getId();
				String taskName = Encodes.urlEncode(delegateTask.getName());
				String taskDefKey = delegateTask.getTaskDefinitionKey();
				String procInsId = delegateTask.getProcessInstanceId();
				String procDefId = delegateTask.getProcessDefinitionId();
				String status = "todo";		//待处理
				String url = MessageFormat.format(urlFormat, ctx, taskId, taskName, taskDefKey, procInsId, procDefId, status);			
				if (StringUtils.equals(assignee, applyUserLoginName)) {

					String subject = "申请退回通知" + "_" + workflowName + "_" + applyUserName;
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("workflowName", workflowName);
					params.put("refundUserName", hisAssigneeName);
					params.put("applyUserName", applyUserName);
					params.put("applyDate", applyDate);
					params.put("url", url);
					
//					SendMailUtil.sendFtlMail(subject, TemplatePath.NORMAL_APPREFUND.value(), params, userApply.getEmail());
					new SendMail(subject, TemplatePath.NORMAL_APPREFUND.value(), params, userApply.getEmail()).run();
				} else {

					String subject = "申请要求审批" + "_" + workflowName + "_" + applyUserName;
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("workflowName", workflowName);
					params.put("applyUserName", applyUserName);
					params.put("applyDate", applyDate);
					params.put("url", url);
					
//					SendMailUtil.sendFtlMail(subject, TemplatePath.NORMAL_APPROVE.value(), params,user.getEmail());
					new SendMail(subject, TemplatePath.NORMAL_APPROVE.value(), params,user.getEmail()).run();
				}
			// add by zhanglulu 20170814 start
			}
			// add by zhanglulu 20170814 end
		}
	}

}
