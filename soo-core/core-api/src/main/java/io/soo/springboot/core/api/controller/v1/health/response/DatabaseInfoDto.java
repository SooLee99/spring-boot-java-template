package io.soo.springboot.core.api.controller.v1.health.response;

/**
 * DatabaseInfoDto
 *
 * @param status 데이터베이스 상태
 * @param version 데이터베이스 버전
 */
public record DatabaseInfoDto(String status, String version) {
}
