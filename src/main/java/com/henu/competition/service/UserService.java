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

import com.henu.competition.model.User;
import com.henu.competition.model.condition.UserCondition;
import com.henu.competition.mapper.UserMapper;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Slf4j
@Service
@Transactional(readOnly = false)
public class UserService extends ServiceImpl<UserMapper, User> {
    /**
     * 分页查询列表
     *
     * @param condition 查询条件
     * @return 分页数据
     */
    public IPage<User> findUserPage(UserCondition condition) {
        IPage<User> page = condition.buildPage();
        return this.baseMapper.findUserList(page, condition);
    }

    /**
     * 查询列表
     * 
     * @param condition 查询条件
     * @return 列表数据
     */
    public List<User> findUserList(UserCondition condition) {
        return this.baseMapper.findUserList(condition);
    }

    /**
     * 查询
     *
     * @param condition 查询条件
     * @return 
     */
    public User getUser(UserCondition condition) {
        List<User> list = this.baseMapper.getUser(condition);
        if (CollUtil.isNotEmpty(list)) {
            if (list.size() > 1) {
                log.warn("Expected one result (or null) to be returned by getUser(), but found: {}", list.size());
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
    public User getUserById(String id) {
        return this.getById(id);
    }

    /**
     * 根据主键ID列表查询列表
     *
     * @param idList 列表
     * @return 列表数据
     */
    public List<User> findUserByIds(List<String> idList) {
        if (CollUtil.isEmpty(idList)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getId, idList.stream().filter(StrUtil::isNotBlank).distinct().collect(Collectors.toList()));
        return this.list(queryWrapper);
    }

    /**
     * 查询主键ID列表对应的集合
     *
     * @param idList 列表
     * @return Map<, >
     */
    public Map<String, User> mapUserByIds(List<String> idList) {
        List<User> list = findUserByIds(idList);
        return Optional.ofNullable(list).orElse(CollUtil.toList()).stream().collect(Collectors.toMap(User::getId, User -> User));
    }

    /**
     * 新增
     *
     * @param user 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addUser(User user) {
        return this.save(user);
    }

    /**
     * 修改
     *
     * @param user 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(User user) {
        return this.updateById(user);
    }

    /**
     * 根据主键ID删除
     *
     * @param id 
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUserById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据主键ID列表批量删除
     *
     * @param idList 列表
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteUserByIds(List<String> idList) {
        return this.removeByIds(idList);
    }
}