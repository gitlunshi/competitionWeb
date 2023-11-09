package com.henu.competition.common.model;

import java.util.Date;
import org.hibernate.validator.constraints.Range;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.hutool.core.date.DatePattern;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体公共字段基础类，用于实体JavaBean继承
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-09
 */
public abstract class BaseBean extends OverrideBeanMethods {
    /** 版本号 */
    private static final long serialVersionUID = 6101499067277733665L;

    /** 创建人，保存用户ID值 */
    @ApiModelProperty(value = "创建人，保存用户ID值", position = 1000)
    @JsonProperty(index = 1000)
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /** 创建日期 */
    @ApiModelProperty(value = "创建日期", position = 1001)
    @JsonProperty(index = 1001)
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT)
    private Date creationDate;

    /** 最后修改人，保存用户ID值 */
    @ApiModelProperty(value = "最后修改人，保存用户ID值", position = 1002)
    @JsonProperty(index = 1002)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdatedBy;

    /** 最后修改日期 */
    @ApiModelProperty(value = "最后修改日期", position = 1003)
    @JsonProperty(index = 1003)
    @JsonFormat(timezone = "GMT+8", pattern = DatePattern.NORM_DATETIME_PATTERN)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastUpdateDate;



    /**
     * 获取记录创建人，保存用户ID值
     *
     * @return 记录创建人
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 设置记录创建人，保存用户ID值
     *
     * @param createdBy 记录创建人，保存用户ID值
     */
    public BaseBean setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * 获取记录创建日期
     *
     * @return 记录创建日期
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * 设置记录创建日期
     *
     * @param creationDate 记录创建日期
     */
    public BaseBean setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * 获取记录最后修改人，保存用户ID值
     *
     * @return 记录最后修改人
     */
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    /**
     * 设置记录最后修改人，保存用户ID值
     *
     * @param lastUpdatedBy 记录最后修改人，保存用户ID值
     */
    public BaseBean setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    /**
     * 获取记录最后修改日期
     *
     * @return 记录最后修改日期
     */
    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    /**
     * 设置记录最后修改日期
     *
     * @param lastUpdateDate 记录最后修改日期
     */
    public BaseBean setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }
}