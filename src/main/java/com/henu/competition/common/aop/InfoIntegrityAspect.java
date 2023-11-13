package com.henu.competition.common.aop;

import cn.hutool.json.JSONUtil;
import com.henu.competition.common.model.InforIntegrityValidator;
import com.henu.competition.common.model.LoginValidator;
import com.henu.competition.common.model.Result;
import com.henu.competition.model.User;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

@Component
@Aspect
@Order(2)
public class InfoIntegrityAspect {

    /**
     * 切点，方法上有注解或者类上有注解
     *  拦截类或者是方法上标注注解的方法
     */
    @Pointcut(value = "@annotation(com.henu.competition.common.model.InforIntegrityValidator) || @within(com.henu.competition.common.model.InforIntegrityValidator)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
        // 获取方法方法上的LoginValidator注解
        MethodSignature methodSignature = (MethodSignature)joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        InforIntegrityValidator inforIntegrityValidator = method.getAnnotation(InforIntegrityValidator.class);
        // 如果有，并且值为false，则不校验
        if (inforIntegrityValidator != null && !inforIntegrityValidator.validated()) {
            return joinpoint.proceed(joinpoint.getArgs());
        }
        // 正常校验 获取request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || requestAttributes.getResponse() == null) {
            // 如果不是从前段过来的，没有request，则直接放行
            return joinpoint.proceed(joinpoint.getArgs());
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        if (
                StringUtils.isBlank(loginUser.getRealName())||
                StringUtils.isBlank(loginUser.getSex())||
                StringUtils.isBlank(loginUser.getNation())||
                StringUtils.isBlank(loginUser.getPhone())||
                StringUtils.isBlank(loginUser.getEmail())||
                StringUtils.isBlank(loginUser.getSNumber())||
                StringUtils.isBlank(loginUser.getSchoolId())||
                StringUtils.isBlank(loginUser.getSicImage())
        ){
            returnIntegrity(response);
            return null;
        }
        return joinpoint.proceed(joinpoint.getArgs());


    }

    /**
     * 信息不完整
     * @param response ServletResponse
     */
    private void returnIntegrity(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置返回401 和响应编码
        response.setStatus(200);
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        Result<Object> objectResult = Result.newInstance()
                .setFlag(false)
                .setCode(1001)
                .setMsg("信息不完整，请先补全用户信息！！");
        String resultString = JSONUtil.toJsonStr(objectResult);
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }

}