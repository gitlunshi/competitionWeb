package com.henu.competition.configuration;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * MyBatis Plus参数配置
 * http://mp.baomidou.com
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Configuration
@MapperScan({"com.henu.competition.mapper"})
public class MybatisPlusConfig {
    /**
     * 初始化分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 初始化公共字段自动填充功能
     */
    @Component
    public class MetaObjectHandlerConfiguration implements MetaObjectHandler {
        @Override
        public void insertFill(MetaObject metaObject) {
            Date today = new Date();
            this.setFieldValByName("creationDate", today, metaObject);
            this.setFieldValByName("lastUpdateDate", today, metaObject);
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("lastUpdateDate", new Date(), metaObject);
        }
    }
}