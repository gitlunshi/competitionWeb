package com.henu.competition.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户请求信息
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRequestInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 66334266866024978L;

    /** 请求路径 */
    private String uri;

    /** 接口描述 */
    private String apiDesc;

    /** 客户端IP */
    private String clientIp;

    /** 浏览器类型 */
    private String browser;

    /** 系统类型 */
    private String os;

    /** 请求方式 */
    private String requestMethod;

    /** 请求参数 */
    private String requestParams;

    /** 请求Body参数 */
    private String requestBody;

    /** 用户代理信息 */
    private String headerUserAgent;

    /** 方法类路径 */
    private String classMethod;

    /** 执行耗时 */
    private Long timeCost;

    /** 错误消息 */
    private String errorMsg;

    /** 错误消息 */
    private String cookie;
}
