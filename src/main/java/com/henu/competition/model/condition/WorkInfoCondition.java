package com.henu.competition.model.condition;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.BaseCondition;

/**
 * work_info查询条件
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-14
 */
@Setter
@Getter
@Builder
@Accessors(chain = true)
@ApiModel(description = "work_info查询条件")
public class WorkInfoCondition extends BaseCondition {
    /** 版本号 */
    private static final long serialVersionUID = 5592185205926590209L;
}