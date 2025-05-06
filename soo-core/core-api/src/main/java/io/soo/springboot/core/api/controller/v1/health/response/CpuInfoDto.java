package io.soo.springboot.core.api.controller.v1.health.response;

/**
 * CPU 부하 정보
 *
 * @param availableProcessors CPU 코어 수
 * @param systemLoadAverage 시스템 1분 평균 부하
 * @param processCpuLoad 프로세스 CPU 사용률 (예: "12.34 %")
 * @param cpuLoad 전체 시스템 CPU 사용률 (예: "27.89 %")
 */
public record CpuInfoDto(int availableProcessors, double systemLoadAverage, String processCpuLoad, String cpuLoad) {
}
