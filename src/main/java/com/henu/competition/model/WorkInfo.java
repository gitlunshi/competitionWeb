package com.henu.competition.model;

import javax.validation.constraints.NotBlank;

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
 * work_info
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-14
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "work_info")
@TableName("work_info")
public class WorkInfo extends BaseBean {
    /** 版本号 */
    private static final long serialVersionUID = 5592185205926590209L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**  */
    @ApiModelProperty(value = "", position = 1)
    @JsonProperty(index = 1)
    @TableId
    private String id;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 2)
    @JsonProperty(index = 2)
    @NotBlank(message = "文件名不能为空！")
    private String fileName;

    /** 战队id */
    @ApiModelProperty(value = "战队id", position = 3)
    @JsonProperty(index = 3)
    @NotBlank(message = "战队id不能为空！")
    private String squadronId;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 4)
    @JsonProperty(index = 4)
    @NotBlank(message = "文件路径不能为空！")
    private String filePath;

    /* This code was generated by TableGo tools, mark 1 end. */
}