package com.inbody.crm.sm.dao;

import java.util.List;

import com.inbody.crm.common.persistence.CrudDao;
import com.inbody.crm.common.persistence.annotation.MyBatisDao;
import com.inbody.crm.sm.domain.SmStorageResume;
import com.inbody.crm.sm.domain.SmStorageSearch;

@MyBatisDao
public interface SmStorageSearchDao extends CrudDao<SmStorageSearch> {

    /** 库存一览分页查询 */
    List<SmStorageSearch> findStoragePage(SmStorageSearch search);

    /** 库存履历查询 */
    List<SmStorageResume> findStorageResume(SmStorageResume resume);

}
