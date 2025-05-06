package io.soo.springboot.core.support;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class LogAspect {

    /*
     * ===== ① 모든 애플리케이션 레이어 ===== - io.soo.springboot 하위 모든 패키지
     */
    @Pointcut("execution(* io.soo.springboot..*(..)) && " + "!execution(* io.soo.springboot.core.support..*(..))")
    public void applicationLayer() {
    }

    /* ===== ② 프레젠테이션 계층(Controller) ===== */
    @Pointcut("execution(* io.soo.springboot..*Controller.*(..))")
    public void controllerLayer() {
    }

    /* ------------- 공통 실행 시간 측정 ------------- */
    @Around("applicationLayer()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        }
        finally {
            long elapsed = System.currentTimeMillis() - start;
            log.info("{} | 처리시간 = {}ms", joinPoint.getSignature(), elapsed);
        }
    }

    /* ------------- HTTP 요청 정보 로깅 ------------- */
    @Around("controllerLayer()")
    public Object loggingBefore(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();

        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Map<String, Object> params = new HashMap<>();

        try {
            String decodedURI = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
            params.put("controller", controllerName);
            params.put("method", methodName);
            params.put("params", getParams(request));
            params.put("requestUri", decodedURI);
            params.put("httpMethod", request.getMethod());
        }
        catch (Exception e) {
            log.error("LogAspect 오류", e);
        }
        log.info("[{}] {} 요청", params.get("httpMethod"), params.get("requestUri"));
        log.info("호출 메서드 : {}.{}", params.get("controller"), params.get("method"));
        log.info("요청 파라미터 : {}", params.get("params"));
        return joinPoint.proceed();
    }

    /* --- 쿼리 파라미터 JSON 변환 --- */
    private static Object getParams(HttpServletRequest request) throws JSONException {
        JSONObject json = new JSONObject();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            json.put(name.replaceAll("\\.", "-"), request.getParameter(name));
        }
        return json;
    }

}
