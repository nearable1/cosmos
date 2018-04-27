package com.inbody.crm.common.persistence;

import java.sql.Date;

import org.apache.commons.lang3.StringUtils;

import com.inbody.crm.common.utils.DateUtils;
import com.inbody.crm.common.utils.IdGen;
import com.inbody.crm.modules.sys.entity.User;
import com.inbody.crm.modules.sys.utils.UserUtils;

/**
 * Entity类期间、区域实现
 * @author nssol.x.lzt
 * @version 2017-05
 * @param <T>
 */
public abstract class TermEntity<T> extends DataEntity<T> {

	private static final long serialVersionUID = 1L;
	
	private String termId;		//期间id
	private String localeId;	//区域语言
	private Date startDate;		//期间开始时间
	private Date endDate;		//期间结束时间
	
	protected static String DEFAULT_LOCALE = "zh_CN"; 
	
	public TermEntity()
	{
		super();
	}
	
	public TermEntity(String id)
	{
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		if(StringUtils.isBlank(this.termId)){
			if(StringUtils.isBlank(this.id))
				setId(IdGen.uuid());
			setTermId(IdGen.uuid());
		}
		if(StringUtils.isBlank(this.localeId))
		{
			setLocaleId(DEFAULT_LOCALE);
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new java.util.Date();
		this.createDate = this.updateDate;
		
		if(StringUtils.isBlank(this.termId)){
			if(StringUtils.isBlank(this.id))
				setId(IdGen.uuid());
			setTermId(IdGen.uuid());
		}
		//如果没有设置区间，则默认为最大区间
		if(this.startDate == null)
			this.startDate = DateUtils.convertToSqlDate(DateUtils.MIN_TERM_DATE);
		if(this.endDate == null)
			this.endDate = DateUtils.convertToSqlDate(DateUtils.MAX_TERM_DATE);
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateDate = new java.util.Date();
		
		//如果没有设置区间，则默认为最大区间
		if(this.startDate == null)
			this.startDate = DateUtils.convertToSqlDate(DateUtils.MIN_TERM_DATE);
		if(this.endDate == null)
			this.endDate = DateUtils.convertToSqlDate(DateUtils.MAX_TERM_DATE);
	}
	
	/**
	 * 是否是新期间
	 * @return
	 */
	public boolean getIsNewTerm(){
		return StringUtils.isBlank(getTermId());
	}
	
	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
