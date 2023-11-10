package com.henu.competition.configuration;

import com.henu.competition.common.model.ConstantValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InitializationConfig implements ApplicationRunner {
    @Value("${file-path}")
    private String filePath;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ConstantValue c=new ConstantValue();
        Class<?> clazz = c.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                String value = (String)field.get(c);
                String path=filePath+value;
                File directory = new File(path);
                if (!directory.exists()) {
                    directory.mkdir();
                    log.info("创建初始化目录："+path);
                }
            } catch (IllegalAccessException e) {
                log.info("无法获取属性值：" + field.getName());
            }
        }
    }
}
