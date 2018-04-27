package com.inbody.crm.sm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.inbody.crm.common.mapper.JsonMapper;
import com.inbody.crm.common.persistence.Page;
import com.inbody.crm.sm.dao.SmStorageSearchDao;
import com.inbody.crm.sm.domain.SmStorageSearch;
import com.inbody.crm.sm.domain.SmStorageSearchExcel;

/**
 * 库存查询
 * 
 * @author yangj
 */
@Service
@Transactional(readOnly = true)
public class SmStorageSearchService {

    @Autowired
    private SmStorageSearchDao smStorageSearchDao;

    /**
     * 查库一览分页查询
     * 
     * @param page
     *            分页信息
     * @param search
     *            查询条件
     * 
     * @return 库存分页数据
     */
    @SuppressWarnings("unchecked")
    public Page<SmStorageSearch> findStoragePage(Page<SmStorageSearch> page,
            SmStorageSearch search) {
        search.setPage(page);
        List<SmStorageSearch> pageList = smStorageSearchDao.findStoragePage(search);

        // 各仓库存存数据解析
        JsonMapper jsonMapperIns = JsonMapper.getInstance();
        JavaType clzType = jsonMapperIns.createCollectionType(HashMap.class, String.class,
                Integer.class);
        for (SmStorageSearch pageItem : pageList) {
            pageItem.setWhMap((Map<String, Integer>) jsonMapperIns.fromJson(
                    "{" + StringUtils.defaultString(pageItem.getWarehouseNum()) + "}",
                    clzType));
        }
        return page.setList(pageList);
    }

    /**
     * 查库一览分页查询
     * 
     * @param page
     *            分页信息
     * @param search
     *            查询条件
     * 
     * @return 库存分页数据
     */
    public List<SmStorageSearchExcel> exportStorageList(
            SmStorageSearch search) throws Exception {
        // 导出数据取得
        List<SmStorageSearch> storageList = smStorageSearchDao.findStoragePage(search);

        // json解析实例
        JsonMapper jsonMapperIns = JsonMapper.getInstance();

        SmStorageSearchExcel excel = null;
        List<SmStorageSearchExcel> excelList = Lists.newArrayList();
        for (SmStorageSearch item : storageList) {
            excel = new SmStorageSearchExcel();

            // 各仓库库存数据解析
            jsonMapperIns.update(
                    "{" + StringUtils.defaultString(item.getWarehouseNum()) + "}", excel);

            excel.setMaterialType(item.getMaterialType());
            excel.setMaterialNo(item.getMaterialNo());
            excel.setMaterialName(item.getMaterialName());
            excel.setModel(item.getModel());
            excel.setTotalNum(item.getTotalNum());
            excel.setInStockNum(item.getInStockNum());
            excel.setTestedNum(item.getTestedNum());
            excel.setAvailableNum(item.getAvailableNum());
            excel.setQuotaOccupyNum(item.getQuotaOccupyNum());
            excel.setInvoicedUndeliveredNum(item.getInvoicedUndeliveredNum());
            excel.setOutStockNum(item.getOutStockNum());
            excel.setLendingNum(item.getLendingNum());
            excel.setUnInvoicedDeliveredNum(item.getUnInvoicedDeliveredNum());
            excel.setSafetyStockNum(item.getSafetyStockNum());
            excel.setNotArrivedNum(item.getNotArrivedNum());

            excelList.add(excel);
        }

        return excelList;
    }
}
