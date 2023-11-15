package com.henu.competition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.competition.model.condition.WorkInfoCondition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.henu.competition.model.WorkInfo;

/**
 * Mapper接口
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-14
 */
public interface WorkInfoMapper extends BaseMapper<WorkInfo> {
    /**
     * 分页查询列表
     * 
     * @param page      分页参数
     * @param condition 查询条件
     * @return 分页数据
     */
    IPage<WorkInfo> findWorkInfoList(IPage<WorkInfo> page, @Param("condition") WorkInfoCondition condition);

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    List<WorkInfo> findWorkInfoList(@Param("condition") WorkInfoCondition condition);

    /**
     * 查询
     * 
     * @param condition 查询条件
     * @return 
     */
    List<WorkInfo> getWorkInfo(@Param("condition") WorkInfoCondition condition);

}