package com.henu.competition.pojo.res;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.henu.competition.common.model.BaseBean;
import com.henu.competition.model.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

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
@ApiModel(description = "SquadronRes")
public class SquadronRes{
    /** 版本号 */
    private static final long serialVersionUID = -2971888522404031760L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**  */
    @ApiModelProperty(value = "", position = 1)
    @JsonProperty(index = 1)
    private String id;

    /** 战队名称 */
    @ApiModelProperty(value = "战队名称", position = 2)
    @JsonProperty(index = 2)
    @NotBlank(message = "战队名称不能为空！")
    private String name;

    /** 选题id */
    @ApiModelProperty(value = "选题id", position = 3)
    @JsonProperty(index = 3)
    private String projectId;

    /** 选题id */
    @ApiModelProperty(value = "选题", position = 6)
    @JsonProperty(index = 6)
    private String project;

    /** 队长标识 */
    @ApiModelProperty(value = "队长标识，1：是，0：不是", position = 4)
    @JsonProperty(index = 4)
    private Boolean leadTag;

    /** 队长标识 */
    @ApiModelProperty(value = "成员信息", position = 5)
    @JsonProperty(index = 5)
    private List<User> Member;

    @ApiModelProperty(value = "队长id", position = 7)
    @JsonProperty(index = 7)
    private String capId;

    /* This code was generated by TableGo tools, mark 1 end. */
}