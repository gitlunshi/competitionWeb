package com.henu.competition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.spring4all.swagger.EnableSwagger2Doc;

/**
 * SmartSports-SpringBoot项目启动类<br/>
 * Swagger2访问地址：http://127.0.0.1:51280/swagger-ui.html
 *  Knife4j访问地址：http://127.0.0.1:51280/doc.html
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@SpringBootApplication
@EnableSwagger2Doc
public class CompetitionSpringbootApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(CompetitionSpringbootApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}