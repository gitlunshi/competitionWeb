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
 * user
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
@ApiModel(description = "user")
@TableName("user")
public class User extends BaseBean {
    /** 版本号 */
    private static final long serialVersionUID = 4292295679076131000L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**  */
    @ApiModelProperty(value = "", position = 1)
    @JsonProperty(index = 1)
    @TableId
    private String id;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 2)
    @JsonProperty(index = 2)
    @NotBlank(message = "用户名不能为空！")
    private String name;

    /** 密码 */
    @ApiModelProperty(value = "密码", position = 3)
    @JsonProperty(index = 3)
    @NotBlank(message = "密码不能为空！")
    private String password;

    /** 真实名称 */
    @ApiModelProperty(value = "真实名称", position = 4)
    @JsonProperty(index = 4)
    private String realName;

    /** 民族 */
    @ApiModelProperty(value = "民族", position = 5)
    @JsonProperty(index = 5)
    private String nation;

    /** 学号 */
    @ApiModelProperty(value = "学号", position = 6)
    @JsonProperty(index = 6)
    private String sNumber;

    /** 学校id */
    @ApiModelProperty(value = "学校id", position = 7)
    @JsonProperty(index = 7)
    @NotBlank(message = "学校id不能为空！")
    private String schoolId;

    /** 学生证图片 */
    @ApiModelProperty(value = "学生证图片", position = 8)
    @JsonProperty(index = 8)
    private String sicImage;

    /* This code was generated by TableGo tools, mark 1 end. */
}