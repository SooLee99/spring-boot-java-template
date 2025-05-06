package io.soo.springboot.core.api.controller.v1.health.response;

public record ThreadInfoDto(
	int threadCount,
	int daemonThreadCount,
	int peakThreadCount,
	long totalStartedThreadCount
) {}
