package com.xeh.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: xeh
 * @date: 2019/1/29 15:49
 * @description: 用户无权限时处理
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //返回json形式的错误信息       
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println("{\"code\":403,\"message\":\"你没有权限访问！\",\"data\":\"\"}");
        response.getWriter().flush();
        /*
        //无权限时跳转
        response.sendRedirect("/home");
        */
        request.getSession().invalidate();
    }
}
