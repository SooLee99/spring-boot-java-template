package io.soo.springboot.core.api.controller.v1.health.response;

import java.util.List;

/**
 * HealthCheckResponseDto
 *
 * @param health 서버 health 상태
 * @param activeProfiles 현재 실행 중인 profile
 * @param appInfo 애플리케이션 정보
 * @param databaseInfo 데이터베이스 정보
 */
public record HealthCheckResponseDto(String health, List<String> activeProfiles, AppInfoDto appInfo,
        DatabaseInfoDto databaseInfo, MemoryInfoDto memoryInfo, ThreadInfoDto threadInfo, OSInfoDto osInfo,
        CpuInfoDto cpuInfo) {
}
