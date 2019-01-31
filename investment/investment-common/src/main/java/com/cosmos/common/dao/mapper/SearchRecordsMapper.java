package com.cosmos.common.dao.mapper;


import com.cosmos.common.entity.pojo.SearchRecords;
import com.cosmos.common.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

     List<String> getHotWords();
}