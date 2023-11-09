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

import com.henu.competition.model.SquadronUserMap;
import com.henu.competition.model.condition.SquadronUserMapCondition;
import com.henu.competition.mapper.SquadronUserMapMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SquadronUserMapService extends ServiceImpl<SquadronUserMapMapper, SquadronUserMap> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<SquadronUserMap> findSquadronUserMapPage(SquadronUserMapCondition condition) {
        IPage<SquadronUserMap> page = condition.buildPage();
        return this.baseMapper.findSquadronUserMapList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<SquadronUserMap> findSquadronUserMapList(SquadronUserMapCondition condition) {
        return this.baseMapper.findSquadronUserMapList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public SquadronUserMap getSquadronUserMap(SquadronUserMapCondition condition) {
        List<SquadronUserMap> list = this.baseMapper.getSquadronUserMap(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getSquadronUserMap(), but found: {}", list.size());
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
    public SquadronUserMap getSquadronUserMapById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<SquadronUserMap> findSquadronUserMapByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SquadronUserMap> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SquadronUserMap::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, SquadronUserMap> mapSquadronUserMapByIds(List<String> idList) {
        List<SquadronUserMap> list = findSquadronUserMapByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(SquadronUserMap::getId, SquadronUserMap -> SquadronUserMap));
    }

    /**
     * 新增
     *
     * @param squadronUserMap 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSquadronUserMap(SquadronUserMap squadronUserMap) {
        return this.save(squadronUserMap);
    }

    /**
     * 修改
     *
     * @param squadronUserMap 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSquadronUserMap(SquadronUserMap squadronUserMap) {
        return this.updateById(squadronUserMap);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSquadronUserMapById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSquadronUserMapByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}