package com.xiaoniu.walking.web.core.aspect;

import java.lang.annotation.*;

/**
 * 操作日志模块信息
 * @author xiangxianjin
 * @date 2019-03-27 14:49 PM
 */

/**
 * 注解会在class中存在，运行时可通过反射获取
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * //目标是方法
 */
@Target(ElementType.METHOD)
@Documented
public @interface ActionLogger
{
    /**
     * 模块名称
     * @return
     */
    String moduleName() default "";

    /**
     * 操作类型
     * @return
     */
    String actionType() default "";
}
