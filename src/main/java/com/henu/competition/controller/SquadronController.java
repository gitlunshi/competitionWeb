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

import com.henu.competition.model.Squadron;
import com.henu.competition.model.condition.SquadronCondition;
import com.henu.competition.service.SquadronService;

/**
 * squadronController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-11-08
 */
@Api(tags = "squadron")
@RestController
@RequestMapping("/squadron")
public class SquadronController extends BaseController {
    @Autowired
    private SquadronService squadronService;

    @ApiOperation(value = "分页查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SquadronCondition", paramType = "body")
    @PostMapping("/findSquadronPage")
    public Paging<Squadron> findSquadronPage(@RequestBody SquadronCondition condition) {
        IPage<Squadron> page = squadronService.findSquadronPage(condition);
        return Paging.buildPaging(page);
    }

    @ApiOperation(value = "查询列表")
    @ApiImplicitParam(name = "condition", value = "查询条件", required = true, dataType = "SquadronCondition", paramType = "body")
    @PostMapping("/findSquadronList")
    public Result<List<Squadron>> findSquadronList(@RequestBody SquadronCondition condition) {
        List<Squadron> list = squadronService.findSquadronList(condition);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据主键ID查询")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/getSquadronById/{id}")
    public Result<Squadron> getSquadronById(@PathVariable String id) {
        Squadron squadron = squadronService.getSquadronById(id);
        return Result.ok(squadron);
    }

    @ApiOperation(value = "新增")
    @ApiImplicitParam(name = "squadron", value = "", required = true, dataType = "Squadron", paramType = "body")
    @PostMapping("/addSquadron")
    public Result<Squadron> addSquadron(@RequestBody @Valid Squadron squadron) {
        Boolean bool = squadronService.addSquadron(squadron);
        if (bool) {
            return Result.ok(squadron);
        }
        return Result.failed();
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParam(name = "squadron", value = "", required = true, dataType = "Squadron", paramType = "body")
    @PutMapping(value = "/updateSquadron")
    public Result<Boolean> updateSquadron(@RequestBody Squadron squadron) {
        Assert.isNotBlank(squadron.getId(), "请选择需要修改的数据！");
        Boolean bool = squadronService.updateSquadron(squadron);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID删除")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @DeleteMapping(value = "/deleteSquadronById/{id}")
    public Result<Boolean> deleteSquadronById(@PathVariable String id) {
        Boolean bool = squadronService.deleteSquadronById(id);
        return Result.okOrFailed(bool);
    }

    @ApiOperation(value = "根据主键ID列表批量删除")
    @ApiImplicitParam(name = "idList", value = "列表", required = true, allowMultiple = true, paramType = "body")
    @DeleteMapping("/deleteSquadronByIds")
    public Result<Boolean> deleteSquadronByIds(@RequestBody List<String> idList) {
        Assert.isNotEmpty(idList, "请选择需要删除的数据！");
        Boolean bool = squadronService.deleteSquadronByIds(idList);
        return Result.okOrFailed(bool);
    }
}