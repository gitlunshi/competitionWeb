package com.henu.competition.controller;

import com.henu.competition.common.model.Result;
import com.henu.competition.service.CommonService;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;


/**
 * userController
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Api(tags = "(admin)adminUser")
@RestController
@Validated

@RequestMapping("/admin/User")
public class UserController{
    @Autowired
    private CommonService commonService;
    @ApiOperation(value = "系统用户登录", notes = "")
    @PostMapping("/userLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "code", value = "验证码", required = true),
    })
    public Result userLogin(@Validated @NotBlank(message = "登录名称不能为空！！") String loginName, @Validated @NotBlank(message = "密码不能为空！！") String password,@Validated @NotBlank(message = "验证码不能为空！！") String code, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String captcha = (String)session.getAttribute("happy-captcha");
        Integer captchaTime = (Integer) session.getAttribute("happy-captcha-time");
        if (captcha==null){
            return Result.failed("没有获取验证码！！！");
        }
        if (captchaTime>30){
            return Result.failed("登陆错误次数已达上限，请重新获取验证码！！！");
        }
        if (!captcha.equals(code)){
            captchaTime+=1;
            session.setAttribute("happy-captcha-time",captchaTime);
            return Result.failed("验证码错误！！！");

        }
        if (!loginName.equals("admin")){
            captchaTime+=1;
            session.setAttribute("happy-captcha-time",captchaTime);
            return Result.failed("用户名不存在！！！");
        }
        if (!password.equals("123qweasd")){
            captchaTime+=1;
            session.setAttribute("happy-captcha-time",captchaTime);
            return Result.failed("密码错误！！！");
        }
        session.setAttribute("loginUser","true");
        session.setAttribute("UpLoadTag","false");
        return Result.ok("登录成功！！！");
    }
}