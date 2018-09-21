package com.cosmos.route.service.impl;

import com.cosmos.route.dao.TypePoemDao;
import com.cosmos.route.entity.TypePoem;
import com.cosmos.route.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoemServiceImpl implements PoemService {
    @Autowired
    private TypePoemDao tyd;

    public List<TypePoem> findByType(int type) {
        return tyd.findByType(type);
    }

    @Override
    public String findByTypeId(int id) {
        return tyd.findByTypeId(id);
    }
}
