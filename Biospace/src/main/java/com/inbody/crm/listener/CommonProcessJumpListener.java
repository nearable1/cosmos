package com.inbody.crm.listener;

import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.task.IdentityLinkType;

import com.google.common.collect.Maps;
import com.inbody.crm.common.constants.CommonConstants;
import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.act.dao.ActDao;
import com.inbody.crm.modules.act.entity.Act;
import com.inbody.crm.modules.act.service.ActTaskService;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

public class CommonProcessJumpListener implements TaskListener {

	private static final long serialVersionUID = 1L;
	private HistoryService historyService = SpringContextHolder.getBean(HistoryService.class);

	private ActTaskService actTaskService = SpringContextHolder.getBean(ActTaskService.class);
	
	private ActDao actDao = SpringContextHolder.getBean(ActDao.class);
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String assignee = delegateTask.getAssignee();
		User user = UserUtils.getByLoginName(assignee);
		
		String proInsId = delegateTask.getProcessInstanceId();
		String proDefId = delegateTask.getProcessDefinitionId();
		String pbkey = delegateTask.getExecution().getProcessBusinessKey();
		Object obPass = delegateTask.getExecution().getVariable("pass");
		String pass = null;
		if (obPass != null) {
			pass = obPass.toString();
		}
		
		if (!StringUtils.equals(pass, "0")) {
			
			String hisAssignee = null;
			
			if(user != null){
				
				HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
				historicActivityInstanceQuery.processInstanceId(proInsId);
				historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
				historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
				List<HistoricActivityInstance> historicActivityInstanceQuerys = historicActivityInstanceQuery.list();
				
				if (historicActivityInstanceQuerys != null && historicActivityInstanceQuerys.size() > 0) {

					hisAssignee = historicActivityInstanceQuerys.get(0).getAssignee();
				}

				Map<String, Object> vars = Maps.newHashMap();
				for (String varName : delegateTask.getExecution().getVariableNames()) {
					vars.put(varName, delegateTask.getExecution().getVariable(varName));
				}
				if (StringUtils.equals(assignee, hisAssignee)) {
					vars.put("pass", "1");

					String[] keys = pbkey.split(":");
					if(keys != null && keys.length >= 2){
						Act act = new Act();
						act.setBusinessTable(keys[0]);
						act.setBusinessId(keys[1]);
						act.setStatus(CommonConstants.WORKFLOW_STATUS_20);
						
						String procDefKey = proDefId.split(":")[0];
						
						//拜访报告的工作流状态存放在status2中
						if(StringUtils.equals(procDefKey, "visiting_report")){
							actDao.updateWorkFlowStatus2ByBusinessId(act);
						}else if (StringUtils.equals(procDefKey, "invoice")){
							String orderNo = actDao.getOrderNoByBusinessId(act.getBusinessId());
							
							act.setBusinessId(orderNo);
							actDao.updateWorkFlowStatusByOrderNo(act);
						}else{
							actDao.updateWorkFlowStatusByBusinessId(act);
						}
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}//休眠一秒钟

					actTaskService.complete(delegateTask.getId(), proInsId, null, vars);
					if (!delegateTask.getCandidates().isEmpty()) {
						
						String groupId = delegateTask.getCandidates().iterator().next().getGroupId();
//						delegateTask.deleteCandidateGroup(groupId);
//						delegateTask.deleteCandidateUser(assignee);
						delegateTask.deleteGroupIdentityLink(groupId, IdentityLinkType.CANDIDATE);
						delegateTask.deleteUserIdentityLink(assignee, IdentityLinkType.CANDIDATE);
					}
				}
			}
		} else if (StringUtils.equals(pass, "0")) {
			delegateTask.getExecution().removeVariable("pass");
		}
	}

}
