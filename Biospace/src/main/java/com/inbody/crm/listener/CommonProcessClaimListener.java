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
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.sys.dao.UserDao;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

public class CommonProcessClaimListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	
	private UserDao userDao = SpringContextHolder.getBean(UserDao.class);

	private ActTaskService actTaskService = SpringContextHolder.getBean(ActTaskService.class);
	
	private RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);

	private HistoryService historyService = SpringContextHolder.getBean(HistoryService.class);
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		if (!delegateTask.getCandidates().isEmpty()) {
			
			String groupId = delegateTask.getCandidates().iterator().next().getGroupId();
			
			List<User> userList = userDao.getUsersByRoleEnname(groupId);
			
			if (userList != null && userList.size() == 1) {
				
//				delegateTask.addCandidateUser(userList.get(0).getLoginName());
				
				actTaskService.claim(delegateTask.getId(), userList.get(0).getLoginName());
//				delegateTask.deleteCandidateGroup(groupId);
			}
			
			if (userList != null && userList.size() > 1) {
				String applyDate = null;
				String procInsId = delegateTask.getProcessInstanceId();
				HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
				historicActivityInstanceQuery.processInstanceId(procInsId);
				historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
				historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
				List<HistoricActivityInstance> historicActivityInstanceQuerys = historicActivityInstanceQuery.list();
				
				if (historicActivityInstanceQuerys != null && historicActivityInstanceQuerys.size() > 0) {

					applyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(historicActivityInstanceQuerys.get(historicActivityInstanceQuerys.size()-1).getStartTime());
				}
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
				String procDefId = delegateTask.getProcessDefinitionId();
				String status = "todo";		//待处理
				String url = MessageFormat.format(urlFormat, ctx, taskId, taskName, taskDefKey, procInsId, procDefId, status);	

				String subject = "申请要求审批" + "_" + workflowName + "_" + applyUserName;
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("workflowName", workflowName);
				params.put("applyUserName", applyUserName);
				params.put("applyDate", applyDate);
				params.put("url", url);

				String[] toMailAddr = new String[userList.size()];;
				for (int i=0; i<userList.size(); i++) {
					
					toMailAddr[i] = userList.get(i).getEmail();
				}
//				SendMailUtil.sendFtlMail(subject, TemplatePath.NORMAL_APPROVE.value(), params, toMailAddr);
				new SendMail(subject, TemplatePath.NORMAL_APPROVE.value(), params, toMailAddr).run();
			}
//			if (userList != null && userList.size() > 1) {
//				
//				delegateTask.addCandidateUser(userList.get(1).getLoginName());
//				
//				actTaskService.claim(delegateTask.getId(), userList.get(1).getLoginName());
//				delegateTask.deleteCandidateGroup(groupId);
//			}
		}
	}

}
