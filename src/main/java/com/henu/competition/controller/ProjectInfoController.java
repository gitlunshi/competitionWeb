package com.henu.competition.controller;

import com.henu.competition.common.model.LoginValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.henu.competition.common.controller.BaseController;
import com.henu.competition.common.model.Paging;
import com.henu.competition.common.model.Result;
import com.henu.competition.common.util.Assert;

import com.henu.competition.model.ProjectInfo;
import com.henu.competition.model.condition.ProjectInfoCondition;
import com.henu.competition.service.ProjectInfoService;

/**
 * project_infoController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-13
 */
@Api(tags = "project_info")
@RestController
@RequestMapping("/projectInfo")
public class ProjectInfoController extends BaseController {
    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation(value = "获取选题列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "ProjectInfoCondition", paramType = "body")
    @GetMapping("/getProjectInfoList")
    @LoginValidator
    public Result<List> getProjectInfoList() {
        List<ProjectInfo> list = projectInfoService.list();
        return Result.ok(list);
    }
}