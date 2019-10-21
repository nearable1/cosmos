package com.xiaoniu.walking.web.core;

import com.xiaoniu.walking.web.core.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: xiangxianjin
 * @date: 2019/4/2 18:42
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@MapperScan(basePackages = "com.xiaoniu.walking.web.core.mapper")
public class SysUserTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void testRole(){
        String userId = "xiangxianjin";
        Set<String> rolesSet = sysUserService.findRoles(userId);

        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("roles", rolesSet);
        Set<String> permsSet = sysUserService.findPermissions(parameters);

        System.out.println("------- test -------");
    }
}
