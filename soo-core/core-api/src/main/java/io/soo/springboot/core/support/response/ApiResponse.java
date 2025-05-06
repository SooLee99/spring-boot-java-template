package io.soo.springboot.core.support.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.soo.springboot.core.support.error.CoreException;
import io.soo.springboot.core.support.error.ErrorCode;
import io.soo.springboot.core.support.error.ErrorMessage;
import lombok.Getter;

@Getter
public class ApiResponse<S> {

    private final ResultType result;

    private final S data;

    private final ErrorMessage error;

    private ApiResponse(ResultType result, S data, ErrorMessage error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> error(String errorCode, String summaryMessage, List<String> detailList, int httpStatus) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(errorCode, summaryMessage, detailList, httpStatus));
    }

    public static Object error(ErrorCode errorCode, Object data) {
        return new ApiResponse<>(ResultType.ERROR, null,
            new ErrorMessage(errorCode, data, errorCode.getHttpStatus().value()));
    }

    public static Object error(ErrorCode errorCode, HttpStatus httpStatus, String message) {
        return new ApiResponse<>(ResultType.ERROR, null,
            new ErrorMessage(errorCode.getMessage(), message, null, httpStatus.value()));
    }

    public static Object error(CoreException e) {
        return new ApiResponse<>(ResultType.ERROR, null,
            new ErrorMessage(e.getErrorCode().getErrorCode(),
				(String)e.getData(), null, e.getErrorCode().getHttpStatus().value()));
    }
}
