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

import com.henu.competition.model.SchoolInfo;
import com.henu.competition.model.condition.SchoolInfoCondition;
import com.henu.competition.mapper.SchoolInfoMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SchoolInfoService extends ServiceImpl<SchoolInfoMapper, SchoolInfo> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<SchoolInfo> findSchoolInfoPage(SchoolInfoCondition condition) {
        IPage<SchoolInfo> page = condition.buildPage();
        return this.baseMapper.findSchoolInfoList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<SchoolInfo> findSchoolInfoList(SchoolInfoCondition condition) {
        return this.baseMapper.findSchoolInfoList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public SchoolInfo getSchoolInfo(SchoolInfoCondition condition) {
        List<SchoolInfo> list = this.baseMapper.getSchoolInfo(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getSchoolInfo(), but found: {}", list.size());
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
    public SchoolInfo getSchoolInfoById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<SchoolInfo> findSchoolInfoByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SchoolInfo> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(SchoolInfo::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, SchoolInfo> mapSchoolInfoByIds(List<String> idList) {
        List<SchoolInfo> list = findSchoolInfoByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(SchoolInfo::getId, SchoolInfo -> SchoolInfo));
    }

    /**
     * 新增
     *
     * @param schoolInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSchoolInfo(SchoolInfo schoolInfo) {
        return this.save(schoolInfo);
    }

    /**
     * 修改
     *
     * @param schoolInfo 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSchoolInfo(SchoolInfo schoolInfo) {
        return this.updateById(schoolInfo);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSchoolInfoById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSchoolInfoByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}