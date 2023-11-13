package com.henu.competition.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.henu.competition.model.ProjectInfo;
import com.henu.competition.model.condition.ProjectInfoCondition;
import com.henu.competition.mapper.ProjectInfoMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-13
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ProjectInfoService extends ServiceImpl<ProjectInfoMapper, ProjectInfo> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<ProjectInfo> findProjectInfoPage(ProjectInfoCondition condition) {
        IPage<ProjectInfo> page = condition.buildPage();
        return this.baseMapper.findProjectInfoList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<ProjectInfo> findProjectInfoList(ProjectInfoCondition condition) {
        return this.baseMapper.findProjectInfoList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public ProjectInfo getProjectInfo(ProjectInfoCondition condition) {
        List<ProjectInfo> list = this.baseMapper.getProjectInfo(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getProjectInfo(), but found: {}", list.size());
            }
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据主键ID查询
     *
     * @param id 
     * @return 
     */
    public ProjectInfo getProjectInfoById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<ProjectInfo> findProjectInfoByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProjectInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(ProjectInfo::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, ProjectInfo> mapProjectInfoByIds(List<String> idList) {
        List<ProjectInfo> list = findProjectInfoByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(ProjectInfo::getId, ProjectInfo -> ProjectInfo));
    }

    /**
     * 新增
     *
     * @param projectInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addProjectInfo(ProjectInfo projectInfo) {
        return this.save(projectInfo);
    }

    /**
     * 修改
     *
     * @param projectInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateProjectInfo(ProjectInfo projectInfo) {
        return this.updateById(projectInfo);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteProjectInfoById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteProjectInfoByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}