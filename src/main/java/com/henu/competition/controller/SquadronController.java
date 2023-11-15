package com.henu.competition.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henu.competition.common.model.InforIntegrityValidator;
import com.henu.competition.common.model.LoginValidator;
import com.henu.competition.model.*;
import com.henu.competition.pojo.req.CreatSquadronReq;
import com.henu.competition.pojo.req.ModifyCapReq;
import com.henu.competition.pojo.res.SquadronRes;
import com.henu.competition.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

import com.henu.competition.model.condition.SquadronCondition;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private SquadronUserMapService squadronUserMapService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private SchoolInfoService schoolInfoService;

    @Autowired
    private WorkInfoService workInfoService;

    @Autowired
    private CommonService commonService;
    @ApiOperation(value = "创建战队并加入")
    @ApiImplicitParam(name = "squadron", value = "", required = true, dataType = "Squadron", paramType = "body")
    @PostMapping("/creatTeam")
    @LoginValidator
    @InforIntegrityValidator
    public Result creatTeam(@RequestBody @Valid CreatSquadronReq creatSquadronReq, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        List<SquadronUserMap> list = squadronUserMapService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            return Result.failed("请先退出已经加入的战队");
        }

        QueryWrapper<Squadron> squadronQueryWrapper=new QueryWrapper<>();
        squadronQueryWrapper.lambda().eq(Squadron::getName,creatSquadronReq.getName());
        List<Squadron> list1 = squadronService.list(squadronQueryWrapper);
        if (CollectionUtils.isNotEmpty(list1)){
            return Result.failed("战队名称已被使用，请更换！！");
        }

        Squadron squadron=new Squadron();
        squadron.setId(UUID.randomUUID().toString());
        squadron.setName(creatSquadronReq.getName());
        squadron.setProjectId(creatSquadronReq.getProjectId());
        squadron.setUserId(user.getId());
        squadron.setCreatedBy(user.getId());
        squadron.setCreationDate(new Date());
        Boolean bool = squadronService.addSquadron(squadron);
        if (!bool) {
            return Result.failed("创建失败！！");
        }

        SquadronUserMap squadronUserMap=new SquadronUserMap();
        squadronUserMap.setId(UUID.randomUUID().toString());
        squadronUserMap.setSquadronId(squadron.getId());
        squadronUserMap.setLeaderTag(1);
        squadronUserMap.setUserId(user.getId());
        squadronUserMap.setCreatedBy(user.getId());
        squadronUserMap.setCreationDate(new Date());
        boolean save = squadronUserMapService.save(squadronUserMap);
        if (!save){
            throw new RuntimeException("创建失败！！");
        }
        return Result.ok("创建成功");
    }

    @ApiOperation(value = "修改战队")
    @ApiImplicitParam(name = "squadron", value = "", required = true, dataType = "Squadron", paramType = "body")
    @PostMapping(value = "/updateSquadron")
    @InforIntegrityValidator
    @LoginValidator
    public Result<Boolean> updateSquadron(@RequestBody @Valid CreatSquadronReq creatSquadronReq, HttpServletRequest request) {
        if (StringUtils.isBlank(creatSquadronReq.getId())){
            return Result.failed("参数错误！！");
        }

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Squadron squadronById = squadronService.getSquadronById(creatSquadronReq.getId());

        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,creatSquadronReq.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getLeaderTag,1);
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);

        if (one==null){
            return Result.failed("只有队长才可以修改战队信息！！");
        }

        BeanUtils.copyProperties(creatSquadronReq,squadronById);

        Boolean b = squadronService.updateSquadron(squadronById);

        if (b){
            return Result.ok();
        }
        return Result.failed("修改失败！！");
    }



    @ApiOperation(value = "删除战队")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/deleteTeam/{id}")
    @LoginValidator
    @InforIntegrityValidator
    public Result<Boolean> deleteSquadronById(@PathVariable String id) {
        if (StringUtils.isBlank(id)){
            return Result.failed("参数错误！！");
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,id);
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getLeaderTag,1);

        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);

        if (one==null){
            return Result.failed("只有队长才解散战队！！");
        }

        QueryWrapper<SquadronUserMap> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.lambda().eq(SquadronUserMap::getSquadronId,id);
        boolean remove = squadronUserMapService.remove(queryWrapper1);

        if (!remove){
            return Result.failed("系统错误！！");
        }

        Boolean b = squadronService.deleteSquadronById(id);

        if (!b){
            throw new RuntimeException("系统错误！！");
        }
        return Result.ok();
    }

    @ApiOperation(value = "退出战队")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/outTeam/{id}")
    @LoginValidator
    @InforIntegrityValidator
    public Result<Boolean> outTeam(@PathVariable String id) {
        if (StringUtils.isBlank(id)){
            return Result.failed("参数错误！！");
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,id);
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getLeaderTag,1);
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);
        if (one!=null){
            return Result.failed("请更换队长后再退出！！");
        }

        queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,id);
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        boolean remove = squadronUserMapService.remove(queryWrapper);

        if (!remove){
            return Result.failed("系统错误！！");
        }

        return Result.ok();
    }

    @ApiOperation(value = "加入战队")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/joinTeam/{id}")
    @LoginValidator
    @InforIntegrityValidator
    public Result<Boolean> joinTeam(@PathVariable String id) {
        if (StringUtils.isBlank(id)){
            return Result.failed("参数错误！！");
        }
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        List<SquadronUserMap> list = squadronUserMapService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            return Result.failed("请先退出已经加入的战队");
        }

        Squadron squadronById = squadronService.getSquadronById(id);
        if (squadronById==null){
            return Result.failed("战队不存在!!");
        }

        SquadronUserMap squadronUserMap=new SquadronUserMap();
        squadronUserMap.setId(UUID.randomUUID().toString());
        squadronUserMap.setSquadronId(squadronById.getId());
        squadronUserMap.setLeaderTag(0);
        squadronUserMap.setUserId(user.getId());
        squadronUserMap.setCreatedBy(user.getId());
        squadronUserMap.setCreationDate(new Date());
        boolean remove = squadronUserMapService.save(squadronUserMap);

        if (!remove){
            return Result.failed("系统错误！！");
        }

        return Result.ok();
    }

    @ApiOperation(value = "获取战队信息")
    @ApiImplicitParam(name = "id", value = "", required = true)
    @GetMapping(value = "/getTeamInfo")
    @LoginValidator
    @InforIntegrityValidator
    public Result<SquadronRes> getTeamInfo() {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);
        if (one==null){
            return Result.ok(null);
        }

        //获取战队信息
        Squadron squadronById = squadronService.getSquadronById(one.getSquadronId());
        ProjectInfo projectInfoById = projectInfoService.getProjectInfoById(squadronById.getProjectId());

        //获取成员
        queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,one.getSquadronId());
        List<SquadronUserMap> list = squadronUserMapService.list(queryWrapper);

        boolean leadTag=false;
        String capId="";
        for (SquadronUserMap s:list
             ) {
            if (s.getLeaderTag()!=1){
                continue;
            }
            if (s.getUserId().equals(user.getId())){
                leadTag=true;
            }
            capId=s.getUserId();
            break;

        }
        List<String> collect = list.stream().map(SquadronUserMap::getUserId).collect(Collectors.toList());
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().in(User::getId,collect);
        List<User> list1 = userService.list(userQueryWrapper);

        List<String> collect1 = list1.stream().map(User::getSchoolId).collect(Collectors.toList());
        List<SchoolInfo> schoolInfos = schoolInfoService.listByIds(collect1);

        for (User u:list1
             ) {
            for (SchoolInfo schoolInfo:schoolInfos
                 ) {
                if(u.getSchoolId().equals(schoolInfo.getId())){
                    u.setSchoolId(schoolInfo.getName());
                }
            }
        }

        SquadronRes squadronRes=new SquadronRes();
        squadronRes.setId(squadronById.getId());
        squadronRes.setName(squadronById.getName());
        squadronRes.setProject(projectInfoById.getName());
        squadronRes.setProjectId(projectInfoById.getId());
        squadronRes.setLeadTag(leadTag);
        squadronRes.setMember(list1);
        squadronRes.setCapId(capId);

        return Result.ok(squadronRes);
    }

    @ApiOperation(value = "修改队长")
    @PostMapping(value = "/modifyCap")
    @LoginValidator
    @InforIntegrityValidator
    public Result<SquadronRes> modifyCap(@RequestBody @Valid ModifyCapReq modifyCapReq) {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,modifyCapReq.getSquadronId());
        queryWrapper.lambda().eq(SquadronUserMap::getLeaderTag,1);
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);
        if (one==null){
            return Result.failed("只有老队长才可以更换队长！！");
        }
        queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,modifyCapReq.getNewCapId());
        queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,modifyCapReq.getSquadronId());
        SquadronUserMap two = squadronUserMapService.getOne(queryWrapper);
        if (two==null){
            return Result.failed("需要更换的用户不在战队中！！");
        }

        one.setLeaderTag(0);
        two.setLeaderTag(1);

        Boolean b = squadronUserMapService.updateSquadronUserMap(one);
        if (!b){
            Result.failed("系统错误！！");
        }
        b = squadronUserMapService.updateSquadronUserMap(two);
        if (!b){
            throw new RuntimeException("系统错误！！");
        }

        return Result.ok();
    }


    @ApiOperation(value = "作品提交")
    @PostMapping(value = "/postWork")
    @LoginValidator
    @InforIntegrityValidator
    public Result<SquadronRes> postWork(MultipartFile data) throws IOException {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        queryWrapper.lambda().eq(SquadronUserMap::getLeaderTag,1);
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);
        if (one==null){
            return Result.failed("只有队长才可以提交作品！！");
        }

        String s = commonService.uploadResources(data, "4");

        WorkInfo workInfo=new WorkInfo();
        workInfo.setId(UUID.randomUUID().toString());
        workInfo.setFileName(data.getOriginalFilename());
        workInfo.setFilePath(s);
        workInfo.setSquadronId(one.getSquadronId());
        workInfo.setCreatedBy(user.getId());
        workInfo.setCreationDate(new Date());
        boolean save = workInfoService.save(workInfo);
        if (save){
            return  Result.ok();
        }
        return Result.failed("上传失败");
    }
}