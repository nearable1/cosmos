package com.inbody.crm.listener;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.inbody.crm.common.utils.StringUtils;
import com.inbody.crm.modules.sys.entity.Role;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * 动态设置审批人，流程中配置在第一个需要动态设置审批人的节点之前的任一节点或连线的执行监听器中
 * 监听器配置后，需动态设置审批人的节点可使用用户变量如下：${bz},${zz}等
 * @author NSSOL.x.lzt
 *
 */
public class CommonAssigneeSetListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Object apply = execution.getVariable("apply");

		if(apply != null){
			String applyUser = apply.toString();
			User zz = UserUtils.getUserGroupCharger(applyUser);
			User gz = UserUtils.getUserGroupsCharger(applyUser);
			User bz = UserUtils.getUserManager(applyUser);
			Map<String, String> vars = new HashMap<String,String>();
			if(bz != null) {
				vars.put("bz", bz.getLoginName());
			} else {
				vars.put("bz", "");
			}
			if(gz != null) {
				vars.put("gz", gz.getLoginName());
				
				// 销售相关如果申请者的角色是事业部及其他销售小组成员的时候，跳过部长审批
				User gzUser = UserUtils.getByLoginName(applyUser);
				if (gzUser != null) {

					for (Role role : gzUser.getRoleList()) {

						if (StringUtils.equals("crm_31", role.getEnname())) {
							vars.put("bz", gz.getLoginName());
						}
					}
				}
			} else {
				vars.put("gz", "");
			}
			if(zz != null) {
				vars.put("zz", zz.getLoginName());
				if(gz == null) {	//Group长不存在时，组长等同于Group长
					vars.put("gz", zz.getLoginName());
				}
			} else {
				vars.put("zz", applyUser);	//组长不存在时，提交给下一级
			}
			execution.setVariables(vars);
			return;
		}
	}

}