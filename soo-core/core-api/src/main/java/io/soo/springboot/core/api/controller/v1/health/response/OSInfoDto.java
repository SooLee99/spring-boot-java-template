package io.soo.springboot.core.api.controller.v1.health.response;

/**
 * 운영체제 정보
 *
 * @param name OS 이름
 * @param version OS 버전
 * @param architecture OS 아키텍처
 * @param availableProcessors CPU 코어 수
 * @param totalPhysicalMemorySize 물리 메모리 총량 (예: "16.00 GB")
 * @param freePhysicalMemorySize 물리 메모리 여유량 (예: "4.25 GB")
 * @param totalSwapSpaceSize 스왑 총량 (예: "2.00 GB")
 * @param freeSwapSpaceSize 스왑 여유량 (예: "1.50 GB")
 */
public record OSInfoDto(String name, String version, String architecture, long availableProcessors,
        String totalPhysicalMemorySize, String freePhysicalMemorySize, String totalSwapSpaceSize,
        String freeSwapSpaceSize) {

}
