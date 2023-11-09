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

import com.henu.competition.model.Squadron;
import com.henu.competition.model.condition.SquadronCondition;
import com.henu.competition.mapper.SquadronMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SquadronService extends ServiceImpl<SquadronMapper, Squadron> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<Squadron> findSquadronPage(SquadronCondition condition) {
        IPage<Squadron> page = condition.buildPage();
        return this.baseMapper.findSquadronList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<Squadron> findSquadronList(SquadronCondition condition) {
        return this.baseMapper.findSquadronList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public Squadron getSquadron(SquadronCondition condition) {
        List<Squadron> list = this.baseMapper.getSquadron(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getSquadron(), but found: {}", list.size());
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
    public Squadron getSquadronById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<Squadron> findSquadronByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Squadron> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Squadron::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, Squadron> mapSquadronByIds(List<String> idList) {
        List<Squadron> list = findSquadronByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(Squadron::getId, Squadron -> Squadron));
    }

    /**
     * 新增
     *
     * @param squadron 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSquadron(Squadron squadron) {
        return this.save(squadron);
    }

    /**
     * 修改
     *
     * @param squadron 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSquadron(Squadron squadron) {
        return this.updateById(squadron);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSquadronById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSquadronByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}