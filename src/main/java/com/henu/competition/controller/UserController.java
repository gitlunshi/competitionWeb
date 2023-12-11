package com.henu.competition.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henu.competition.common.controller.BaseController;
import com.henu.competition.common.model.LoginValidator;
import com.henu.competition.common.model.Result;
import com.henu.competition.common.util.StringTools;
import com.henu.competition.model.*;
import com.henu.competition.pojo.req.ModifyUserReq;
import com.henu.competition.pojo.req.UserSigUpReq;
import com.henu.competition.pojo.res.HomePageRes;
import com.henu.competition.service.*;
import io.swagger.annotations.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * userController
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Api(tags = "user")
@RestController
@Validated
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolInfoService schoolInfoService;

    @Autowired
    private SquadronService squadronService;

    @Autowired
    private SquadronUserMapService squadronUserMapService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping("/userLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    public Result userLogin(@Validated @NotBlank(message = "登录名称不能为空！！") String loginName, @Validated @NotBlank(message = "密码不能为空！！") String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        String password2md5=null;
        try {
            password2md5 = DigestUtils.md5Hex(password.getBytes("UTF-8"));
        }catch (Exception e){
            return Result.failed("系统错误，请联系管理员！！");
        }
        queryWrapper.lambda().eq(User::getName,loginName);
        queryWrapper.lambda().eq(User::getPassword, password2md5);
        User one = userService.getOne(queryWrapper);
        if (one==null){
            return Result.failed("用户名或者密码错误！！");
        }
        session.setAttribute("user",one);
        return Result.ok("登录成功！！！");
    }

    @ApiOperation(value = "获取用户信息", notes = "")
    @GetMapping("/getUserInfo")
    @LoginValidator
    public Result<User> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        User r=new User();
        BeanUtils.copyProperties(user,r);
        SchoolInfo byId = schoolInfoService.getById(r.getSchoolId());
        if (byId==null){
            return Result.failed("系统错误！！");
        }
        r.setSchoolId(byId.getCode());
        return Result.ok(r);
    }

    @ApiOperation(value = "退出登录", notes = "")
    @GetMapping("/userLogout")
    public Result userLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return Result.ok();
    }

    @ApiOperation(value = "查询用户是否登录", notes = "")
    @GetMapping("/isLogin")
    public Result isLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        if (user!=null){
            return Result.ok(true);
        }else{
            return Result.ok(false);
        }
    }

    @ApiOperation(value = "用户信息修改", notes = "")
    @PostMapping("/modifyUserInfo")
    @LoginValidator
    public Result userLoginOut(@Valid @RequestBody ModifyUserReq modifyUserReq, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");


        QueryWrapper<SchoolInfo> schoolInfoQueryWrapper=new QueryWrapper<>();
        schoolInfoQueryWrapper.lambda().eq(SchoolInfo::getCode,modifyUserReq.getSchoolId());
        SchoolInfo one = schoolInfoService.getOne(schoolInfoQueryWrapper);
        if (one==null){
            return Result.failed("参数错误！！");
        }

        User r=new User();
        BeanUtils.copyProperties(user,r);
        BeanUtils.copyProperties(modifyUserReq,r);
        r.setSchoolId(one.getId());
        Boolean b1 = userService.deleteUserById(r.getId());
        if (b1){
            boolean save = userService.save(user);
            if (save){
                session.setAttribute("user",r);
                return Result.ok();
            }else{
                throw new RuntimeException("系统错误");
            }
        }
        return Result.failed();
    }

    @ApiOperation(value = "用户注册", notes = "")
    @PostMapping("/userSigUp")
    public Result userSigUp(@Valid @RequestBody UserSigUpReq userSigUpReq, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean verification = commonService.verification( request,userSigUpReq.getCode());
        if (!verification){
            session.removeAttribute("happy-captcha");
            Result result=new Result();
            result.setCode(1001);
            result.setMsg("验证码错误！！");
            return result;
        }
        boolean b = StringTools.checkPassword(userSigUpReq.getPassword());
        if (!b){
            return Result.failed("密码不合法，必须为8至20位，由小写字母，大写字母，数字和特殊字符中的两种组合！！");
        }
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getName,userSigUpReq.getName());
        List<User> list = userService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)){
            return Result.failed("用户名已经被使用，请更换！！");
        }
        QueryWrapper<SchoolInfo> schoolInfoQueryWrapper=new QueryWrapper<>();
        schoolInfoQueryWrapper.lambda().eq(SchoolInfo::getCode,userSigUpReq.getSchoolId());
        SchoolInfo one = schoolInfoService.getOne(schoolInfoQueryWrapper);
        if (one==null){
            return Result.failed("参数错误！！");
        }
        String password2md5=null;
        try {
            password2md5 = DigestUtils.md5Hex(userSigUpReq.getPassword().getBytes("UTF-8"));
        }catch (Exception e){
            return Result.failed("系统错误，请联系管理员！！");
        }
        User user=new User();
        user.setName(userSigUpReq.getName());
        user.setPassword(password2md5);
        user.setId(UUID.randomUUID().toString());
        user.setSchoolId(one.getId());
        user.setCreatedBy(user.getId());
        user.setCreationDate(new Date());
        boolean save = userService.save(user);
        session.removeAttribute("happy-captcha");
        if (!save){
            return Result.failed("注册失败请重试！！");
        }
        session.setAttribute("user",user);
        return Result.ok("成功！！！");
    }

    @ApiOperation(value = "修改密码", notes = "")
    @PostMapping("/modifyPassword")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPasswprd", value = "老密码", required = true),
            @ApiImplicitParam(name = "newPasswprd", value = "新密码", required = true),
    })
    @LoginValidator
    public Result modifyPassword(@Validated @NotBlank(message = "老密码不能为空！！") String oldPasswprd, @Validated @NotBlank(message = "新密码不能为空！！") String newPasswprd, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        QueryWrapper<User> queryWrapper=new QueryWrapper();
        String password2md5=null;
        try {
            password2md5 = DigestUtils.md5Hex(oldPasswprd.getBytes("UTF-8"));
        }catch (Exception e){
            return Result.failed("系统错误，请联系管理员！！");
        }

        if (!password2md5.equals(user.getPassword())){
            return Result.failed("老密码错误！！");
        }

        try {
            password2md5 = DigestUtils.md5Hex(newPasswprd.getBytes("UTF-8"));
        }catch (Exception e){
            return Result.failed("系统错误，请联系管理员！！");
        }
        User n=new User();
        n.setId(user.getId());
        n.setPassword(password2md5);

        boolean b = userService.updateById(n);
        if (!b){
            return Result.failed("修改失败！！");
        }
        session.removeAttribute("user");
        return Result.ok("修改成功！！！");
    }

    @ApiOperation(value = "首页信息", notes = "")
    @GetMapping("/getHomePage")
    @LoginValidator
    public Result<HomePageRes> getHomePage() {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        User resultUser=new User();
        BeanUtils.copyProperties(user,resultUser);

        QueryWrapper<SchoolInfo> schoolInfoQueryWrapper=new QueryWrapper<>();
        schoolInfoQueryWrapper.lambda().eq(SchoolInfo::getId,user.getSchoolId());
        SchoolInfo one1 = schoolInfoService.getOne(schoolInfoQueryWrapper);
        resultUser.setSchoolId(one1.getName());

        HomePageRes h=new HomePageRes();
        h.setUser(resultUser);


        QueryWrapper<SquadronUserMap> queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SquadronUserMap::getUserId,user.getId());
        SquadronUserMap one = squadronUserMapService.getOne(queryWrapper);
        if(one!=null){
            Squadron squadronById = squadronService.getSquadronById(one.getSquadronId());
            ProjectInfo projectInfoById = projectInfoService.getProjectInfoById(squadronById.getProjectId());

            queryWrapper=new QueryWrapper<>();
            queryWrapper.lambda().eq(SquadronUserMap::getSquadronId,squadronById.getId());
            List<SquadronUserMap> list = squadronUserMapService.list(queryWrapper);

            h.setName(squadronById.getName());
            h.setProject(projectInfoById.getName());
            h.setMembers(list.size());
        }
        return Result.ok(h);
    }


}