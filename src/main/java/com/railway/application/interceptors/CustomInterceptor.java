package com.railway.application.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Component
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());

        request.setAttribute("startTime",System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long)request.getAttribute("startTime");
        long duration=System.currentTimeMillis()-startTime;
        System.out.println("Time taken :" +duration+" in ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long)request.getAttribute("startTime");
        long duration=System.currentTimeMillis()-startTime;
        System.out.println("Time taken :" +duration+" in ms");
    }
}
