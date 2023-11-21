package com.henu.competition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.competition.model.condition.SystemSettingCondition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.henu.competition.model.SystemSetting;

/**
 * Mapper接口
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-21
 */
public interface SystemSettingMapper extends BaseMapper<SystemSetting> {
    /**
     * 分页查询列表
     * 
     * @param page      分页参数
     * @param condition 查询条件
     * @return 分页数据
     */
    IPage<SystemSetting> findSystemSettingList(IPage<SystemSetting> page, @Param("condition") SystemSettingCondition condition);

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    List<SystemSetting> findSystemSettingList(@Param("condition") SystemSettingCondition condition);

    /**
     * 查询
     * 
     * @param condition 查询条件
     * @return 
     */
    List<SystemSetting> getSystemSetting(@Param("condition") SystemSettingCondition condition);

}