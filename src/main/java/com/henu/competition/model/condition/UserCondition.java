package com.henu.competition.model.condition;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.BaseCondition;

/**
 * user查询条件
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Setter
@Getter
@Builder
@Accessors(chain = true)
@ApiModel(description = "user查询条件")
public class UserCondition extends BaseCondition {
    /** 版本号 */
    private static final long serialVersionUID = 8809791091247374442L;
}