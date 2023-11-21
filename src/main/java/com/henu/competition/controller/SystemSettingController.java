package com.henu.competition.controller;

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

import com.henu.competition.model.SystemSetting;
import com.henu.competition.model.condition.SystemSettingCondition;
import com.henu.competition.service.SystemSettingService;

/**
 * system_SettingController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-21
 */
@RestController
@RequestMapping("/systemInfo")
public class SystemSettingController extends BaseController {
    @Autowired
    private SystemSettingService systemSettingService;


    @ApiOperation(value = "是否到达上传作品时间！！")
    @GetMapping("/isPostWorkTime")
    public Result<Boolean> isPostWorkTime() {
        return Result.ok(systemSettingService.isPostWorks());
    }
}