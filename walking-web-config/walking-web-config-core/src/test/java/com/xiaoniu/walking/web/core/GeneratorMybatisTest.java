package com.xiaoniu.walking.web.core;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GeneratorMybatisTest {

    @Test
    public void myBatisGenerator() {
        try (InputStream is = GeneratorMybatisTest.class.getClassLoader().getResourceAsStream("generatorConfig.xml")){
            List<String> warnings = new ArrayList<>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            for (String warning : warnings) {
                System.out.println(warning);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}