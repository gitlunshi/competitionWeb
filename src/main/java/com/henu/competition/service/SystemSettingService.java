package com.henu.competition.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.henu.competition.model.SystemSetting;
import com.henu.competition.model.condition.SystemSettingCondition;
import com.henu.competition.mapper.SystemSettingMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-21
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SystemSettingService extends ServiceImpl<SystemSettingMapper, SystemSetting> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<SystemSetting> findSystemSettingPage(SystemSettingCondition condition) {
        IPage<SystemSetting> page = condition.buildPage();
        return this.baseMapper.findSystemSettingList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<SystemSetting> findSystemSettingList(SystemSettingCondition condition) {
        return this.baseMapper.findSystemSettingList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public SystemSetting getSystemSetting(SystemSettingCondition condition) {
        List<SystemSetting> list = this.baseMapper.getSystemSetting(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getSystemSetting(), but found: {}", list.size());
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
    public SystemSetting getSystemSettingById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<SystemSetting> findSystemSettingByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SystemSetting> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SystemSetting::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, SystemSetting> mapSystemSettingByIds(List<String> idList) {
        List<SystemSetting> list = findSystemSettingByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(SystemSetting::getId, SystemSetting -> SystemSetting));
    }

    /**
     * 新增
     *
     * @param systemSetting 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSystemSetting(SystemSetting systemSetting) {
        return this.save(systemSetting);
    }

    /**
     * 修改
     *
     * @param systemSetting 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSystemSetting(SystemSetting systemSetting) {
        return this.updateById(systemSetting);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSystemSettingById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSystemSettingByIds(List<String> idList) {
        return this.removeByIds(idList);
    }

    public Boolean isPostWorks(){
        QueryWrapper<SystemSetting> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SystemSetting::getName,"postWorksTime");
        SystemSetting one = this.getOne(queryWrapper);
        long l = Long.parseLong(one.getValue());
        Date date1 = new Date();
        return l<date1.getTime();
    }
}