package com.henu.competition.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.henu.competition.common.model.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.OverrideBeanMethods;

/**
 * squadron
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-09
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "squadron")
@TableName("squadron")
public class Squadron extends BaseBean {
    /** 版本号 */
    private static final long serialVersionUID = -2971888522404031760L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**  */
    @ApiModelProperty(value = "", position = 1)
    @JsonProperty(index = 1)
    @TableId
    private String id;

    /** 战队名称 */
    @ApiModelProperty(value = "战队名称", position = 2)
    @JsonProperty(index = 2)
    @NotBlank(message = "战队名称不能为空！")
    private String name;

    /** 选题id */
    @ApiModelProperty(value = "选题id", position = 3)
    @JsonProperty(index = 3)
    @NotBlank(message = "选题id不能为空！")
    private String projectId;

    /** 创建人id */
    @ApiModelProperty(value = "创建人id", position = 4)
    @JsonProperty(index = 4)
    @NotBlank(message = "创建人id不能为空！")
    private String userId;

    /* This code was generated by TableGo tools, mark 1 end. */
}