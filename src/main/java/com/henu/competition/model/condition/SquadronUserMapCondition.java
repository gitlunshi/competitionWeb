package com.henu.competition.model.condition;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.BaseCondition;

/**
 * squadron_user_map查询条件
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Setter
@Getter
@Builder
@Accessors(chain = true)
@ApiModel(description = "squadron_user_map查询条件")
public class SquadronUserMapCondition extends BaseCondition {
    /** 版本号 */
    private static final long serialVersionUID = -3500107913031711097L;
}