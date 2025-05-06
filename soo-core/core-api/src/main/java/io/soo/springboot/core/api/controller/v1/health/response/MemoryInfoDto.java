package io.soo.springboot.core.api.controller.v1.health.response;

/**
 * JVM 메모리 사용량 정보
 *
 * @param totalMemory 총 메모리 (예: "256.00 MB")
 * @param freeMemory 여유 메모리 (예: "128.50 MB")
 * @param maxMemory 최대 사용할 수 있는 메모리 (예: "512.00 MB")
 */
public record MemoryInfoDto(String totalMemory, String freeMemory, String maxMemory) {
}
