package com.henu.competition.service;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Slf4j
@Service
public class CommonService {
    @Value("${file-path}")
    private String filePath;


    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
                .style(CaptchaStyle.IMG)            //设置展现样式为图片
                .length(4)                            //设置字符长度为4
                .width(220)                            //设置动画宽度为220
                .height(80)                            //设置动画高度为80
                .build().finish();                  //生成并输出验证码;
        String captcha = (String)request.getSession().getAttribute("happy-captcha");
        request.getSession().setAttribute("happy-captcha-time",0);
        log.info("生成的验证码为："+captcha);
    }

    /**
     * 确认验证码
     *
     * @param code 验证码字符串
     * @return 验证结果
     */
    public Boolean verification(HttpServletRequest request, String code) {
        return HappyCaptcha.verification(request, code, true);
    }

    /**
     * 上传资源文件
     *
     * @param data     文件
     * @return 正常返回文件资源url, 异常则返回null
     */
    public Boolean uploadVideo(MultipartFile data) {
        String originalFilename = data.getOriginalFilename();
        String[] split = originalFilename.split("\\.");

        try {
            data.transferTo(Paths.get(filePath + split[0]+"-"+new Date().getTime()+"."+split[1]));
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}