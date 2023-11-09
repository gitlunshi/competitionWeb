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

import com.henu.competition.model.SquadronUserMap;
import com.henu.competition.model.condition.SquadronUserMapCondition;
import com.henu.competition.service.SquadronUserMapService;

/**
 * squadron_user_mapController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Api(tags = "squadron_user_map")
@RestController
@RequestMapping("/squadronUserMap")
public class SquadronUserMapController extends BaseController {
    @Autowired
    private SquadronUserMapService squadronUserMapService;

    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SquadronUserMapCondition", paramType = "body")
    @PostMapping("/findSquadronUserMapPage")
    public Paging<SquadronUserMap> findSquadronUserMapPage(@RequestBody SquadronUserMapCondition condition) {
        IPage<SquadronUserMap> page = squadronUserMapService.findSquadronUserMapPage(condition);
        return Paging.buildPaging(page);
    }

    @ApiOperation(value = "查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SquadronUserMapCondition", paramType = "body")
    @PostMapping("/findSquadronUserMapList")
    public Result<List<SquadronUserMap>> findSquadronUserMapList(@RequestBody SquadronUserMapCondition condition) {
        List<SquadronUserMap> list = squadronUserMapService.findSquadronUserMapList(condition);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据主键ID查询")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/getSquadronUserMapById/{id}")
    public Result<SquadronUserMap> getSquadronUserMapById(@PathVariable String id) {
        SquadronUserMap squadronUserMap = squadronUserMapService.getSquadronUserMapById(id);
        return Result.ok(squadronUserMap);
    }

    @ApiOperation(value = "新增")
    @ApiImplicitParam(name = "squadronUserMap", value = "", required = true, dataType = "SquadronUserMap", paramType = "body")
    @PostMapping("/addSquadronUserMap")
    public Result<SquadronUserMap> addSquadronUserMap(@RequestBody @Valid SquadronUserMap squadronUserMap) {
        Boolean bool = squadronUserMapService.addSquadronUserMap(squadronUserMap);
        if (bool) {
            return Result.ok(squadronUserMap);
        }
        return Result.failed();
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParam(name = "squadronUserMap", value = "", required = true, dataType = "SquadronUserMap", paramType = "body")
    @PutMapping(value = "/updateSquadronUserMap")
    public Result<Boolean> updateSquadronUserMap(@RequestBody SquadronUserMap squadronUserMap) {
        Assert.isNotBlank(squadronUserMap.getId(), "请选择需要修改的数据！");
        Boolean bool = squadronUserMapService.updateSquadronUserMap(squadronUserMap);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID删除")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @DeleteMapping(value = "/deleteSquadronUserMapById/{id}")
    public Result<Boolean> deleteSquadronUserMapById(@PathVariable String id) {
        Boolean bool = squadronUserMapService.deleteSquadronUserMapById(id);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID列表批量删除")
    @ApiImplicitParam(name = "idList", value = "列表", required = true, allowMultiple = true, paramType = "body")
    @DeleteMapping("/deleteSquadronUserMapByIds")
    public Result<Boolean> deleteSquadronUserMapByIds(@RequestBody List<String> idList) {
        Assert.isNotEmpty(idList, "请选择需要删除的数据！");
        Boolean bool = squadronUserMapService.deleteSquadronUserMapByIds(idList);
        return Result.okOrFailed(bool);
    }
}