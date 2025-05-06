package io.soo.springboot.core.support.interceptor;

import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AdminAuthorizationInterceptor1 {

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

}
