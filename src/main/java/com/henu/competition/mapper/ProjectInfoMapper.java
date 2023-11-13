package com.henu.competition.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.henu.competition.model.condition.ProjectInfoCondition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.henu.competition.model.ProjectInfo;

/**
 * Mapper接口
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-13
 */
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {
    /**
     * 分页查询列表
     * 
     * @param page      分页参数
     * @param condition 查询条件
     * @return 分页数据
     */
    IPage<ProjectInfo> findProjectInfoList(IPage<ProjectInfo> page, @Param("condition") ProjectInfoCondition condition);

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    List<ProjectInfo> findProjectInfoList(@Param("condition") ProjectInfoCondition condition);

    /**
     * 查询
     * 
     * @param condition 查询条件
     * @return 
     */
    List<ProjectInfo> getProjectInfo(@Param("condition") ProjectInfoCondition condition);

}