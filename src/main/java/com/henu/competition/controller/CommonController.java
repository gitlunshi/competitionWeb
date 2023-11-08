package com.henu.competition.controller;

import com.henu.competition.common.controller.BaseController;
import com.henu.competition.common.model.Result;
import com.henu.competition.service.CommonService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * userController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Api(tags = "common")
@RestController
@Validated
@RequestMapping("/common")
public class CommonController extends BaseController {
    @Autowired
    private CommonService commonService;


    @ApiOperation(value = "获取验证码",notes = "返回结果是一张图片，4位验证码，验证码宽度为220*80")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest reqeust, HttpServletResponse response){
        commonService.captcha(reqeust,response);
    }

    @ApiOperation(value = "确认验证码")
    @GetMapping("/verification")
    @ApiImplicitParam(name = "code", value = "", required = true)
    public Result<Boolean> verification(HttpServletRequest reqeust, String code){
        Result<Boolean> r=Result.ok();
        boolean b=commonService.verification(reqeust,code);
       return r.setData(b);
    }

    @ApiOperation(value = "上传资源")
    @PostMapping("/uploadResources")
    @ApiImplicitParam(name = "fileType", value = "文件类型,1:视频，2：图片，3：文档，4：其他", required = true)
    public Result<String> uploadVideo(HttpServletRequest request,MultipartFile data) throws IOException {
        String contentType = data.getContentType();
        HttpSession session = request.getSession();
        String loginUser = (String)session.getAttribute("loginUser");
        String UpLoadTag = (String)session.getAttribute("UpLoadTag");
        if (!"text/plain".equals(contentType)){
            return Result.failed("文件格式错误！！");
        }
        if (loginUser==null){
            return Result.failed("请登录！！");
        }
        if (UpLoadTag.equals("true")){
            return Result.failed("已经上传过一次，如需重新上传，请重新登录！！");
        }
        if (!loginUser.equals("true")){
            return Result.failed("系统错误，请稍后重试！！");
        }
        Boolean s = commonService.uploadVideo(data);
        if (s){
            session.setAttribute("UpLoadTag","true");
            return Result.ok();
        }
        return Result.failed("上传失败");
    }
}