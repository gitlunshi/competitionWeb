package com.henu.competition.common.model;

import java.lang.annotation.*;

/**
 * @description 登录校验注解，用户aop校验
 * @author Yalu Wang
 * @email 104752200075@henu.edu.cn
 * @date Created in 2023/9/1 下午9:35
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InforIntegrityValidator {
    boolean validated() default true;
}
