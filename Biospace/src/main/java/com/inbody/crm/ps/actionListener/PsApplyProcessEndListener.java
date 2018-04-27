package com.inbody.crm.ps.actionListener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import com.inbody.crm.common.utils.SpringContextHolder;
import com.inbody.crm.ps.dao.PsApplyPriceDtlDao;

public class PsApplyProcessEndListener implements ExecutionListener {

	private static final long serialVersionUID = 1L;
	
	private PsApplyPriceDtlDao dao = SpringContextHolder.getBean(PsApplyPriceDtlDao.class);

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String pbkey = execution.getProcessBusinessKey();
		String[] keys = pbkey.split(":");
		if(keys != null && keys.length >= 2){
			dao.onProcessEnd(keys[1]);
		}
	}

}
