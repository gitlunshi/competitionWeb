package com.henu.competition.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.henu.competition.common.exception.BizException;
import com.henu.competition.common.model.ConstantValue;
import com.henu.competition.model.SchoolInfo;
import com.henu.competition.model.Squadron;
import com.henu.competition.model.SquadronUserMap;
import com.henu.competition.model.User;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Service接口实现
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Slf4j
@Service
@Transactional
public class CommonService {
    @Value("${file-path}")
    private String filePath;

    @Autowired
    UserService userService;

    @Autowired
    SquadronService squadronService;

    @Autowired
    SquadronUserMapService squadronUserMapService;

    @Autowired
    SchoolInfoService schoolInfoService;
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request, response)
                .style(CaptchaStyle.IMG)            //设置展现样式为图片
                .length(4)                            //设置字符长度为4
                .width(220)                            //设置动画宽度为220
                .height(80)                            //设置动画高度为80
                .build().finish();                  //生成并输出验证码;
        String captcha = (String)request.getSession().getAttribute("happy-captcha");
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
     * @param fileType 文件类型,1:视频，2：图片，3：文档，4：其他
     * @return 正常返回文件资源url, 异常则返回null
     */
    public String uploadResources(MultipartFile data, String fileType) throws IOException {
        String originalFilename = data.getOriginalFilename();
        String[] split = originalFilename.split("\\.");
        String filename = UUID.randomUUID().toString();
        if (split.length >= 2) {
            filename = filename + "." + split[split.length - 1];
        }
        data.transferTo(Paths.get(type2path(fileType) + filename));
        String resourceUrl = "/common/resourceFile/" + filename;
        return resourceUrl;
    }

    public void resourceFile(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path=type2path("2")+file;
            this.responseFile(path,response);
    }

    public void downloadCompettionDoc(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path=filePath+ConstantValue.COMP_DOC_PATH+file;
        this.responseFile(path,response);
    }

    public void responseFile(String path,HttpServletResponse response) {
        File file = new File(path);
        //如果文件不存在，就不用往下执行了。
        if (!file.exists())
            throw new BizException("文件不存在");
        //用最基本的io流来将图片输出
        ServletOutputStream output = null;
        FileInputStream input = null;
        MimetypesFileTypeMap mimetypesFileTypeMap=new MimetypesFileTypeMap();
        //设置相应头尾Mime类型的
        String mimeType = mimetypesFileTypeMap.getContentType(file);
        //设置返回的内容
        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());
        try {
            output = response.getOutputStream();
            input = new FileInputStream(file);
            int i = 0;
            while ( (i = input.read()) != -1){
                output.write(i);
            }
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String type2path(String fileType){
        String lFilePath = "";
        if ("1".equals(fileType)) {
            lFilePath = ConstantValue.VIDEO_SAVE_PATH;
        }
        if ("2".equals(fileType)) {
            lFilePath = ConstantValue.IMG_SAVE_PATH;
        }
        if ("3".equals(fileType)) {
            lFilePath = ConstantValue.DOC_SAVE_PATH;
        }
        if ("4".equals(fileType)) {
            lFilePath = ConstantValue.OTHER_SAVE_PATH;
        }
        return filePath + lFilePath;
    }

    public Map bigScreenPlay(){


        List<User> list1 = userService.list();
        QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.lambda().isNotNull(User::getRealName);
        List<User> list2 = userService.list(userQueryWrapper);
        long qNum = squadronService.count();
        long qmNUm = squadronUserMapService.count();

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("DISTINCT school_id");
        List<String> list = (List<String>)(List) userService.listObjs(queryWrapper);

        List<Map<String,Object>> tableData=new ArrayList<>();

        List<SchoolInfo> schoolInfos = schoolInfoService.listByIds(list);

        for (SchoolInfo s:schoolInfos
             ) {
            Map<String,Object> map=new HashMap<>();
            map.put("schoolName",s.getName());
            int t=0;
            for (User u:list1
                 ) {
                if (s.getId().equals(u.getSchoolId())){
                    t++;
                }
            }
            map.put("registerNum",t);
            t=0;
            for (User u:list2
            ) {
                if (s.getId().equals(u.getSchoolId())){
                    t++;
                }
            }
            map.put("realyNameNum",t);
            tableData.add(map);
        }



        Map<String,Object> map=new HashMap<>();
        map.put("registerNum",list1.size());
        map.put("realyNameNum",list2.size());
        map.put("qNum",qNum);
        map.put("qAvgNum",(double)qmNUm/(double)qNum);
        map.put("tableData",tableData);
        return map;
    }

}