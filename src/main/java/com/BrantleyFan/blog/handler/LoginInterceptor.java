package com.BrantleyFan.blog.handler;

import com.BrantleyFan.blog.pojo.SysUser;
import com.BrantleyFan.blog.service.LoginService;
import com.BrantleyFan.blog.utils.UserThreadLocal;
import com.BrantleyFan.blog.vo.ErrorCode;
import com.BrantleyFan.blog.vo.Result;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行controller方法之前进行执行
        /**
         * 1.判断请求的接口路径是否为HandlerMethod（controller方法）
         * 2.判断token是否为空，如果为空 未登录
         * 3.如果token不为空，登陆验证loginService checkToken
         * 4.如果token认证成功 放行
         */

        if (!(handler instanceof HandlerMethod)){
            // hander 可能是 RequestResourceHandler
            return true;
        }
        String token = request.getHeader("Authorization");

        // 日志 方便查看
        log.info("=============request start==================");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("=============request end==================");

        if (StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charaset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charaset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }

        UserThreadLocal.put(sysUser);

        // 登陆验证成功 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果不删除 ThreadLocal中用户的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
