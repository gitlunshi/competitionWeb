package com.henu.competition.common.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.OS;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentInfo;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.henu.competition.common.model.UserRequestInfo;
import com.henu.competition.common.util.RequestUtils;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * 日志切面拦截器
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /** 配置Controller方法的切入点 */
    @Pointcut("execution(* *..controller..*Controller.*(..))")
    public void controllerPoint() {
    }

    /**
     * 配置Controller请求方法的环绕通知
     *
     * @param joinPoint ProceedingJoinPoint
     */
    @Around("controllerPoint()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestUtils.getRequest();
        if (request == null) {
            return joinPoint.proceed();
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String uri = request.getRequestURI();
        String classMethod = String.format("%s.%s", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        String requestMethod = request.getMethod();

        String apiDesc = null;
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        if (annotation != null) {
            apiDesc = annotation.value();
        }
        JSONObject requestParams = new JSONObject();
        Map<String, String> paramMap = ServletUtil.getParamMap(request);
        if (MapUtil.isNotEmpty(paramMap)) {
            requestParams = JSONUtil.parseObj(paramMap);
        }
        String requestBody = null;
        if (!StrUtil.equalsAnyIgnoreCase(requestMethod, ServletUtil.METHOD_GET, ServletUtil.METHOD_DELETE)) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                Object obj = args[0];
                if (obj instanceof MultipartFile) {
                    MultipartFile file = ((MultipartFile) obj);
                    if (!file.isEmpty()) {
                        JSONObject fileJson = new JSONObject();
                        fileJson.set("fileName", file.getOriginalFilename());
                        fileJson.set("fileSize", String.valueOf(file.getSize()));
                        fileJson.set("fileType", file.getContentType());
                        requestParams.set("file", fileJson);
                    }
                } else if (obj instanceof List) {
                    List<JSONObject> fileList = CollUtil.newArrayList();
                    List<?> list = (List<?>) obj;
                    for (Object o : list) {
                        if (o instanceof MultipartFile) {
                            MultipartFile file = ((MultipartFile) o);
                            if (!file.isEmpty()) {
                                JSONObject fileJson = new JSONObject();
                                fileJson.set("fileName", file.getOriginalFilename());
                                fileJson.set("fileSize", String.valueOf(file.getSize()));
                                fileJson.set("fileType", file.getContentType());
                                fileList.add(fileJson);
                            }
                        }
                    }
                    if (CollUtil.isNotEmpty(fileList)) {
                        requestParams.set("fileList", fileList);
                    }
                } else {
                    requestBody = JSONUtil.toJsonStr(obj);
                }
            }
        }
        String clientIp = ServletUtil.getClientIP(request);
        String browserName = null;
        String osName = null;
        String headerUserAgent = RequestUtils.getHeaderUserAgent(request);
        String headerCookie = RequestUtils.getHeaderCookie(request);
        if (StrUtil.isNotBlank(headerUserAgent)) {
            UserAgent userAgent = RequestUtils.getUserAgent(headerUserAgent);
            if (userAgent != null) {
                Browser browser = userAgent.getBrowser();
                if (browser != null && StrUtil.isNotBlank(browser.getName()) && !StrUtil.equalsAnyIgnoreCase(browser.getName(), UserAgentInfo.NameUnknown)) {
                    browserName = browser.getName();
                }
                String version = userAgent.getVersion();
                if (StrUtil.isAllNotBlank(version, browserName) && !StrUtil.equalsAnyIgnoreCase(version, UserAgentInfo.NameUnknown)) {
                    browserName += StrUtil.SPACE + version;
                }
                OS os = userAgent.getOs();
                if (os != null && StrUtil.isNotBlank(os.getName()) && !StrUtil.equalsAnyIgnoreCase(os.getName(), UserAgentInfo.NameUnknown)) {
                    osName = os.getName();
                }
            }
        }
        // 初始化用户请求信息
        UserRequestInfo userRequestInfo = UserRequestInfo.builder()
                .uri(uri)
                .apiDesc(apiDesc)
                .clientIp(clientIp)
                .browser(browserName)
                .os(osName)
                .requestMethod(requestMethod)
                .requestParams(requestParams.isEmpty() ? null : requestParams.toString())
                .requestBody(requestBody)
                .headerUserAgent(headerUserAgent)
                .classMethod(classMethod)
                .cookie(headerCookie)
                .build();

        Instant begin = Instant.now();
        try {
           // 执行目标方法并获取返回结果
           Object result = joinPoint.proceed();
            Instant end = Instant.now();
            Long timeCost = Duration.between(begin, end).toMillis();
            userRequestInfo.setTimeCost(timeCost);

            logger.info("UserRequestInfo: {}", JSONUtil.toJsonStr(userRequestInfo));
            return result;
        } catch (Exception e) {
            Instant end = Instant.now();
            Long timeCost = Duration.between(begin, end).toMillis();
            userRequestInfo.setTimeCost(timeCost).setErrorMsg(e.getMessage());
            logger.error("UserRequestInfo: {}", JSONUtil.toJsonStr(userRequestInfo));
            throw e;
        }
    }
}