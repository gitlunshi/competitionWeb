package com.henu.competition.controller;

import com.henu.competition.common.controller.BaseController;
import com.henu.competition.common.model.LoginValidator;
import com.henu.competition.common.model.Result;
import com.henu.competition.model.User;
import com.henu.competition.service.CommonService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
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
    @LoginValidator
    public Result<String> uploadResources(HttpServletRequest request,MultipartFile data,String fileType) throws IOException {
        if (StringUtils.isBlank(fileType)||!("1".equals(fileType)||"2".equals(fileType)||"3".equals(fileType)||"4".equals(fileType))){
            return Result.failed("文件类型错误");
        }
        HttpSession session = request.getSession();
        User loginUser = (User)session.getAttribute("user");
        if (loginUser==null){
            return Result.failed("上传失败");
        }
        String s = commonService.uploadResources(data, fileType);
        if (s!=null){
            return Result.ok(s);
        }
        return Result.failed("上传失败");
    }
    @ApiOperation(value = "获取资源文件",notes = "直接返回资源文件，如果是视频文件则支持流媒体断点续传，请求头需要包含Rang字段")
    @GetMapping("/resourceFile/{file}")
    @ApiImplicitParam(name = "file", value = "文件名,带后缀", required = true)
    @LoginValidator
    public void resourceFile(@PathVariable @NotBlank String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        commonService.resourceFile(file,request,response);
    }
}