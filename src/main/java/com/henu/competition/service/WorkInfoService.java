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

import com.henu.competition.model.WorkInfo;
import com.henu.competition.model.condition.WorkInfoCondition;
import com.henu.competition.mapper.WorkInfoMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-14
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class WorkInfoService extends ServiceImpl<WorkInfoMapper, WorkInfo> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<WorkInfo> findWorkInfoPage(WorkInfoCondition condition) {
        IPage<WorkInfo> page = condition.buildPage();
        return this.baseMapper.findWorkInfoList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<WorkInfo> findWorkInfoList(WorkInfoCondition condition) {
        return this.baseMapper.findWorkInfoList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public WorkInfo getWorkInfo(WorkInfoCondition condition) {
        List<WorkInfo> list = this.baseMapper.getWorkInfo(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getWorkInfo(), but found: {}", list.size());
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
    public WorkInfo getWorkInfoById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<WorkInfo> findWorkInfoByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<WorkInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(WorkInfo::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, WorkInfo> mapWorkInfoByIds(List<String> idList) {
        List<WorkInfo> list = findWorkInfoByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(WorkInfo::getId, WorkInfo -> WorkInfo));
    }

    /**
     * 新增
     *
     * @param workInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addWorkInfo(WorkInfo workInfo) {
        return this.save(workInfo);
    }

    /**
     * 修改
     *
     * @param workInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWorkInfo(WorkInfo workInfo) {
        return this.updateById(workInfo);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWorkInfoById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteWorkInfoByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}