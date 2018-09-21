package com.cosmos.route.controller;

import com.cosmos.route.entity.TypePoem;
import com.cosmos.route.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoemController {

    @Autowired
    private PoemService ps;

    @RequestMapping("index")
    public TypePoem findPoem(@RequestParam("type") int type) {
        List<TypePoem> list = ps.findByType(type);
        int index = (int) (Math.random()* list.size());

        return list.get(index);
    }

    @RequestMapping("type")
    public String findType(@RequestParam("id") int id) {
        String str = ps.findByTypeId(id);

        return str;
    }

}
