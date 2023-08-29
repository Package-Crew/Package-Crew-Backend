package com.hufs.bhackathon.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다"),
    USER_ALREADY_EXISTS(CONFLICT, "이미 존재하는 사용자입니다"),
    WORK_NOT_FOUND(NOT_FOUND, "작업을 찾을 수 없습니다"),
    ITEM_NOT_FOUND(NOT_FOUND, "상품을 찾을 수 없습니다"),
    DELIVERY_NOT_FOUND(NOT_FOUND, "배송을 찾을 수 없습니다"),
    WORKER_NOT_FOUND(NOT_FOUND, "작업자를 찾을 수 없습니다"),
    FAIL_IMAGE_UPLOAD(INTERNAL_SERVER_ERROR, "이미지 업로드 실패");


    private final HttpStatus httpStatus;
    private final String message;
}
