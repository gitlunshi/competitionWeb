package com.henu.competition.model.condition;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.henu.competition.common.model.BaseCondition;

/**
 * school_info查询条件
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Setter
@Getter
@Builder
@Accessors(chain = true)
@ApiModel(description = "school_info查询条件")
public class SchoolInfoCondition extends BaseCondition {
    /** 版本号 */
    private static final long serialVersionUID = 8274127571507697459L;
}