package com.henu.competition.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henu.competition.common.model.Result;
import com.henu.competition.common.util.StringTools;
import com.henu.competition.model.SchoolInfo;
import com.henu.competition.model.User;
import com.henu.competition.pojo.req.ModifyUserReq;
import com.henu.competition.pojo.req.UserSigUpReq;
import com.henu.competition.service.CommonService;
import com.henu.competition.service.SchoolInfoService;
import com.henu.competition.service.UserService;
import io.swagger.annotations.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController{
    @Autowired
    private CommonService commonService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchoolInfoService schoolInfoService;


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

    @ApiOperation(value = "用户信息修改", notes = "")
    @PostMapping("/modifyUserInfo")
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
        Boolean b = userService.updateById(user);
        if (b){
            session.setAttribute("user",r);
            return Result.ok();
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
}