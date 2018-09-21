package com.cosmos.route.service;

import com.cosmos.route.entity.TypePoem;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PoemService {
    List<TypePoem> findByType(int type);

    String findByTypeId(int id);
}
