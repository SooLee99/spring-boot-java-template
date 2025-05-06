package io.soo.springboot.core.api.controller.v1.health.response;

import java.time.Instant;

/**
 * 애플리케이션 메타 정보
 *
 * @param timestamp 애플리케이션 시작 시간
 * @param uptime    애플리케이션 가동 시간 (예: "1234 ms")
 * @param version   애플리케이션 버전
 */
public record AppInfoDto(
	Instant timestamp,
	String uptime,
	String version
) {}
