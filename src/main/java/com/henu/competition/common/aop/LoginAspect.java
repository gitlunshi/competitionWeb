package com.henu.competition.common.aop;

import cn.hutool.json.JSONUtil;
import com.henu.competition.common.model.LoginValidator;
import com.henu.competition.common.model.Result;
import com.henu.competition.model.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
public class LoginAspect {

    /**
     * 切点，方法上有注解或者类上有注解
     *  拦截类或者是方法上标注注解的方法
     */
    @Pointcut(value = "@annotation(com.henu.competition.common.model.LoginValidator) || @within(com.henu.competition.common.model.LoginValidator)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object before(ProceedingJoinPoint joinpoint) throws Throwable {
        // 获取方法方法上的LoginValidator注解
        MethodSignature methodSignature = (MethodSignature)joinpoint.getSignature();
        Method method = methodSignature.getMethod();
        LoginValidator loginValidator = method.getAnnotation(LoginValidator.class);
        // 如果有，并且值为false，则不校验
        if (loginValidator != null && !loginValidator.validated()) {
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
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser!=null){
            return joinpoint.proceed(joinpoint.getArgs());
        }
        return null;

    }

    /**
     * 返回未登录的错误信息
     * @param response ServletResponse
     */
    private void returnNoLogin(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置返回401 和响应编码
        response.setStatus(200);
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        Result<Object> objectResult = Result.newInstance()
                .setFlag(false)
                .setCode(1000)
                .setMsg("未登陆，请先登陆");
        String resultString = JSONUtil.toJsonStr(objectResult);
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }

}