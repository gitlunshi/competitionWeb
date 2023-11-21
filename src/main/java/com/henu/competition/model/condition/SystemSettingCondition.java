package com.henu.competition.model.condition;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.BaseCondition;

/**
 * system_Setting查询条件
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-21
 */
@Setter
@Getter
@Builder
@Accessors(chain = true)
@ApiModel(description = "system_Setting查询条件")
public class SystemSettingCondition extends BaseCondition {
    /** 版本号 */
    private static final long serialVersionUID = -4255367834850160643L;
}