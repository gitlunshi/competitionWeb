package com.henu.competition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.competition.model.condition.UserCondition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.henu.competition.model.User;

/**
 * Mapper接口
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 分页查询列表
     * 
     * @param page      分页参数
     * @param condition 查询条件
     * @return 分页数据
     */
    IPage<User> findUserList(IPage<User> page, @Param("condition") UserCondition condition);

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    List<User> findUserList(@Param("condition") UserCondition condition);

    /**
     * 查询
     * 
     * @param condition 查询条件
     * @return 
     */
    List<User> getUser(@Param("condition") UserCondition condition);

}