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

import com.henu.competition.model.SchoolInfo;
import com.henu.competition.model.condition.SchoolInfoCondition;
import com.henu.competition.service.SchoolInfoService;

/**
 * school_infoController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Api(tags = "school_info")
@RestController
@RequestMapping("/schoolInfo")
public class SchoolInfoController extends BaseController {
    @Autowired
    private SchoolInfoService schoolInfoService;

    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SchoolInfoCondition", paramType = "body")
    @PostMapping("/findSchoolInfoPage")
    public Paging<SchoolInfo> findSchoolInfoPage(@RequestBody SchoolInfoCondition condition) {
        IPage<SchoolInfo> page = schoolInfoService.findSchoolInfoPage(condition);
        return Paging.buildPaging(page);
    }

    @ApiOperation(value = "查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SchoolInfoCondition", paramType = "body")
    @PostMapping("/findSchoolInfoList")
    public Result<List<SchoolInfo>> findSchoolInfoList(@RequestBody SchoolInfoCondition condition) {
        List<SchoolInfo> list = schoolInfoService.findSchoolInfoList(condition);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据主键ID查询")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/getSchoolInfoById/{id}")
    public Result<SchoolInfo> getSchoolInfoById(@PathVariable String id) {
        SchoolInfo schoolInfo = schoolInfoService.getSchoolInfoById(id);
        return Result.ok(schoolInfo);
    }

    @ApiOperation(value = "新增")
    @ApiImplicitParam(name = "schoolInfo", value = "", required = true, dataType = "SchoolInfo", paramType = "body")
    @PostMapping("/addSchoolInfo")
    public Result<SchoolInfo> addSchoolInfo(@RequestBody @Valid SchoolInfo schoolInfo) {
        Boolean bool = schoolInfoService.addSchoolInfo(schoolInfo);
        if (bool) {
            return Result.ok(schoolInfo);
        }
        return Result.failed();
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParam(name = "schoolInfo", value = "", required = true, dataType = "SchoolInfo", paramType = "body")
    @PutMapping(value = "/updateSchoolInfo")
    public Result<Boolean> updateSchoolInfo(@RequestBody SchoolInfo schoolInfo) {
        Assert.isNotBlank(schoolInfo.getId(), "请选择需要修改的数据！");
        Boolean bool = schoolInfoService.updateSchoolInfo(schoolInfo);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID删除")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @DeleteMapping(value = "/deleteSchoolInfoById/{id}")
    public Result<Boolean> deleteSchoolInfoById(@PathVariable String id) {
        Boolean bool = schoolInfoService.deleteSchoolInfoById(id);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID列表批量删除")
    @ApiImplicitParam(name = "idList", value = "列表", required = true, allowMultiple = true, paramType = "body")
    @DeleteMapping("/deleteSchoolInfoByIds")
    public Result<Boolean> deleteSchoolInfoByIds(@RequestBody List<String> idList) {
        Assert.isNotEmpty(idList, "请选择需要删除的数据！");
        Boolean bool = schoolInfoService.deleteSchoolInfoByIds(idList);
        return Result.okOrFailed(bool);
    }
}