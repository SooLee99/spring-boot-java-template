package io.soo.springboot.core.support.error;

import java.util.List;

import lombok.Getter;

@Getter
public class ErrorMessage {

    private final String code;

    private final String message;

    private final Object data;
    private final int httpStatus;

    public ErrorMessage(ErrorCode errorCode, int httpStatus) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
		this.httpStatus = httpStatus;
		this.data = null;
    }

    public ErrorMessage(ErrorCode errorCode, Object data, int httpStatus) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.data = data;
		this.httpStatus = httpStatus;
	}

    public ErrorMessage(String errorCode, String summaryMessage, List<String> detailList, int httpStatus) {
        this.code = errorCode;
        this.message = summaryMessage;
        this.data = detailList;
        this.httpStatus = httpStatus;
    }
}
