package com.henu.competition;

import java.util.UUID;

public class Test2 {
    public static void main(String[] args) {
        String []s={
                "微信小程序开发-1",
                "WEB开发-1",
                "安卓和IOS开发-1",
                "人脸识别与替换-2",
                "实时体育动作识别-2",
                "人体三维建模-2"
        };

        for (String ss:s
             ) {
            String[] split = ss.split("-");
            System.out.println("INSERT INTO project_info (id,name,sectionalization,created_by,creation_date) VALUES ('"+ UUID.randomUUID().toString() +"','"+split[0]+"',"+split[1]+",'00000','2023-11-13 14:40:00');");
        }
    }
}
