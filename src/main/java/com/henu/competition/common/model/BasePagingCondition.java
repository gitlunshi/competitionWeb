package com.henu.competition.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页查询请求参数，用于继承
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
public abstract class BasePagingCondition extends OverrideBeanMethods {
    /** 版本号 */
    private static final long serialVersionUID = 8051759355564013572L;

    /** 每页显示多少条记录 */
    @ApiModelProperty(value = "每页显示多少条记录", example = "20", position = 1000)
    @JsonProperty(index = 1000)
    private Long pageSize = 20L;

    /** 当前页 */
    @ApiModelProperty(value = "当前页", example = "1", position = 1001)
    @JsonProperty(index = 1001)
    private Long page = 1L;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段", position = 1002)
    @JsonProperty(index = 1002)
    private String sortField;

    /** 排序方式，只能是ASC或DESC */
    @ApiModelProperty(value = "排序方式，只能是ASC或DESC", position = 1003)
    @JsonProperty(index = 1003)
    private String sortOrder;

    /**
     * 获取每页显示多少条记录
     *
     * @return 每页显示多少条记录
     */
    public Long getPageSize() {
        return this.pageSize;
    }

    /**
     * 设置每页显示多少条记录
     *
     * @param pageSize
     *          每页显示多少条记录
     */
    public BasePagingCondition setPageSize(Long pageSize) {
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        }
        return this;
    }

    /**
     * 获取当前页
     *
     * @return 当前页
     */
    public Long getPage() {
        return this.page;
    }

    /**
     * 设置当前页
     *
     * @param page
     *          当前页
     */
    public BasePagingCondition setPage(Long page) {
        if (page != null && page > 0) {
            this.page = page;
        }
        return this;
    }

    /**
     * 获取排序字段
     *
     * @return 排序字段
     */
    public String getSortField() {
        return this.sortField;
    }

    /**
     * 设置排序字段
     *
     * @param sortField
     *          排序字段
     */
    public BasePagingCondition setSortField(String sortField) {
        this.sortField = sortField;
        return this;
    }

    /**
     * 获取排序方式，只能是ASC或DESC
     *
     * @return 排序方式
     */
    public String getSortOrder() {
        return this.sortOrder;
    }

    /**
     * 设置排序方式，只能是ASC或DESC
     *
     * @param sortOrder
     *          排序方式
     */
    public BasePagingCondition setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    /** 创建简单分页模型 */
    public <T> Page<T> buildPage() {
        return new Page<>(page, pageSize);
    }
}