package com.inbody.crm.sp.dao;

import java.util.Date;
import java.util.List;

import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sp.domain.SpSalesResume;

/**
 * 销售履历dao
 * 
 * @author yangj
 *
 */
@MyBatisDao
public interface SpSalesResumeDao {

    /** 根据指定的成交月查询销售履历数据 */
    List<SpSalesResume> getSalesResumeExcelData(Date expTurnoverMonth);

}
