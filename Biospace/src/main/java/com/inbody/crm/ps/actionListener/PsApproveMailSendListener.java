package com.inbody.crm.ps.actionListener;

//import java.text.MessageFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.activiti.engine.HistoryService;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.delegate.DelegateTask;
//import org.activiti.engine.delegate.TaskListener;
//import org.activiti.engine.history.HistoricActivityInstance;
//import org.activiti.engine.history.HistoricActivityInstanceQuery;
//import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
//
//import com.inbody.crm.common.config.Global;
//import com.inbody.crm.common.constants.TemplatePath;
//import com.inbody.crm.common.utils.Encodes;
//import com.inbody.crm.common.utils.SendMail;
//import com.inbody.crm.common.utils.SpringContextHolder;
//import com.inbody.crm.common.utils.StringUtils;
//import com.inbody.crm.modules.sys.dao.UserDao;
//import com.inbody.crm.modules.sys.entity.User;
//import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 某个节点审批结束后给固定角色发送邮件
 * @author Administrator
 *
 */
//public class PsApproveMailSendListener implements TaskListener {
//
//	private static final long serialVersionUID = 1L;
//
//	private RepositoryService repositoryService = SpringContextHolder.getBean(RepositoryService.class);
//	private HistoryService historyService = SpringContextHolder.getBean(HistoryService.class);
//	private UserDao userDao = SpringContextHolder.getBean(UserDao.class);
//	
//	@Override
//	public void notify(DelegateTask delegateTask){
//		Object obPass = delegateTask.getExecution().getVariable("pass");
//		String pass = null;
//		if (obPass != null) {
//			pass = obPass.toString();
//		}
//		
//		if (StringUtils.equals(pass, "0")) {
//			return;
//		}
//
//		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) 
//				repositoryService.getProcessDefinition(delegateTask.getProcessDefinitionId());
//
//		String workflowName = processDefinition.getName();
//		String workflowKey = processDefinition.getKey();
//		String roleEnname = "";
//		
//		// 价格申请
//		if(StringUtils.equals(workflowKey, "ps_apply")){
//			roleEnname = "crm-12";//管理部-运营组长
//		}
//
//		List<User> userList = userDao.getUsersByRoleEnname(roleEnname);
//		List<String> mailToList = new ArrayList<String>();
//		if(userList != null && userList.size() > 0){
//			for(User user:userList){
//				if(StringUtils.isNotBlank(user.getEmail())){
//					mailToList.add(user.getEmail());
//				}
//			}
//		}
//		if(mailToList.size()>0){
//			HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
//			historicActivityInstanceQuery.processInstanceId(delegateTask.getProcessInstanceId());
//			historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().desc();
//			historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().asc();
//			List<HistoricActivityInstance> historicActivityInstanceQuerys = historicActivityInstanceQuery.list();
//			
//			String applyDate = null;
//			if (historicActivityInstanceQuerys != null && historicActivityInstanceQuerys.size() > 0) {
//
//				applyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(historicActivityInstanceQuerys.get(historicActivityInstanceQuerys.size()-1).getStartTime());
//			}
//
//			String applyUserLoginName = delegateTask.getVariable("apply").toString();
//			User userApply=UserUtils.getByLoginName(applyUserLoginName);
//			String applyUserName = userApply.getName();
//			
//			//String applyNo = "";
////			String applyDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(delegateTask.getCreateTime());
//			
//			String urlFormat = "{0}/act/task/form?taskId={1}&taskName={2}&taskDefKey={3}&procInsId={4}&procDefId={5}&status={6}";
//			String ctx = Global.getHost() + Global.getAdminPath();
//			String taskId = delegateTask.getId();
//			String taskName = Encodes.urlEncode(delegateTask.getName());
//			String taskDefKey = delegateTask.getTaskDefinitionKey();
//			String procInsId = delegateTask.getProcessInstanceId();
//			String procDefId = delegateTask.getProcessDefinitionId();
////			String status = "todo";		//待处理
//			String status = "finish";
//			String url = MessageFormat.format(urlFormat, ctx, taskId, taskName, taskDefKey, procInsId, procDefId, status);			
//			
//			String subject = "申请审批通知" + "_" + workflowName + "_" + applyUserName;
//			
//			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("workflowName", workflowName);
//			params.put("applyUserName", applyUserName);
//			//params.put("applyNo", "applyNo");
//			params.put("applyDate", applyDate);
//			params.put("url", url);
//			
////			SendMailUtil.sendFtlMail(subject, TemplatePath.NORMAL_APPROVE.value(), 
////					params,mailToList.toArray(new String[mailToList.size()]));
//			new SendMail(subject, TemplatePath.NORMAL_APPROVE.value(), 
//					params,mailToList.toArray(new String[mailToList.size()])).run();
//			
//		}
//	}
//}
