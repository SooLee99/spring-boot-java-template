package io.soo.springboot.core.support;

import io.soo.springboot.core.support.error.CoreException;
import io.soo.springboot.core.support.error.ErrorCode;
import io.soo.springboot.core.support.response.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreException(CoreException e) {
		String exceptionLog = "CoreException : {}";
		switch (e.getErrorCode().getLogLevel()) {
            case ERROR -> log.error(exceptionLog, e.getMessage(), e);
            case WARN -> log.warn(exceptionLog, e.getMessage(), e);
            default -> log.info(exceptionLog, e.getMessage(), e);
        }
        return new ResponseEntity<>((HttpStatusCode)ApiResponse.error(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>((HttpStatusCode)ApiResponse.error(
                ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
