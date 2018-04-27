package com.inbody.crm.common.constants;

public enum TemplatePath {
	/**
	 * 常规审批提醒邮件模板
	 */
	NORMAL_APPROVE("mailtemplate/rove.ftl")
	
	/**
	 * 常规退回提醒邮件模板
	 */
	,NORMAL_APPREFUND("mailtemplate/refund.ftl")
	
	/**
	 * 常规申请结束提醒邮件模板
	 */
	,NORMAL_APPOVER("mailtemplate/over.ftl")
	
	/**
	 * 参照邮件模板
	 */
	,NORMAL_REF("mailtemplate/test3.ftl")
	
	/**
	 * 
	 */
	,NORMAL_SENDBACK("mailtemplate/test2.ftl")

    /**
     * 协议到期提醒邮件模板
     */
    ,AGREEMENT_EXPIRE_REMIND("mailtemplate/agreementExpireRemind.ftl")

    /**
     * 样机借用到期提醒邮件模板
     */
    ,BORROW_EXPIRES_REMIND("mailtemplate/borrowExpiresRemind.ftl")

    /**
     * 合同收款滞纳提醒邮件模板
     */
    ,CONTRACT_LATE_PAYMENT_REMIND("mailtemplate/contractLatePaymentRemind.ftl")

    /**
     * 安装信息未返回提醒邮件模板
     */
    ,INSTALL_NO_FEEDBACK_REMIND("mailtemplate/installNoFeedbackRemind.ftl")

    /**
     * 报价单收款滞纳提醒邮件模板
     */
    ,QUOTA_LATE_PAYMENT_REMIND("mailtemplate/quotaLatePaymentRemind.ftl");

	/**
	 * 消息类型值
	 */
	private String value;

	/**
	 * 构造函数
	 * 
	 * @param value
	 *            邮件模板路径
	 */
	TemplatePath(String value) {
		this.value = value;
	}

	/**
	 * 返回邮件模板路径
	 * 
	 * @return 邮件模板路径
	 */
	public String value() {
		return value;
	}
};
