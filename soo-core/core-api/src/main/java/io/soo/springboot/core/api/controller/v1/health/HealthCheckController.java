package io.soo.springboot.core.api.controller.v1.health;

import io.soo.springboot.core.api.controller.v1.health.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import com.sun.management.OperatingSystemMXBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HealthCheckController {

    private final Environment environment;

    private final DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {
        AppInfoDto appInfo = buildAppInfo();
        DatabaseInfoDto dbInfo = buildDatabaseInfo();
        MemoryInfoDto memoryInfo = buildMemoryInfo();
        ThreadInfoDto threadInfo = buildThreadInfo();
        OSInfoDto osInfo = buildOsInfo();
        CpuInfoDto cpuInfo = buildCpuInfo();
        List<String> profiles = getActiveProfiles();

        HealthCheckResponseDto response = new HealthCheckResponseDto("UP", profiles, appInfo, dbInfo, memoryInfo,
                threadInfo, osInfo, cpuInfo);
        return ResponseEntity.ok(response);
    }

    private AppInfoDto buildAppInfo() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        Instant startTime = Instant.ofEpochMilli(runtime.getStartTime());
        String uptime = runtime.getUptime() + " ms";
        String version = environment.getProperty("spring.application.version", "unknown") + " version";
        return new AppInfoDto(startTime, uptime, version);
    }

    private DatabaseInfoDto buildDatabaseInfo() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            String ver = meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion();
            return new DatabaseInfoDto("UP", ver);
        }
        catch (Exception e) {
            return new DatabaseInfoDto("DOWN", e.getMessage());
        }
    }

    private MemoryInfoDto buildMemoryInfo() {
        Runtime rt = Runtime.getRuntime();
        String total = formatMb(rt.totalMemory());
        String free = formatMb(rt.freeMemory());
        String max = formatMb(rt.maxMemory());
        return new MemoryInfoDto(total, free, max);
    }

    private ThreadInfoDto buildThreadInfo() {
        ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
        return new ThreadInfoDto(tmx.getThreadCount(), tmx.getDaemonThreadCount(), tmx.getPeakThreadCount(),
                tmx.getTotalStartedThreadCount());
    }

    private OSInfoDto buildOsInfo() {
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        return new OSInfoDto(os.getName(), os.getVersion(), os.getArch(), os.getAvailableProcessors(),
                formatGb(os.getTotalMemorySize()), formatGb(os.getFreeMemorySize()),
                formatGb(os.getTotalSwapSpaceSize()), formatGb(os.getFreeSwapSpaceSize()));
    }

    private CpuInfoDto buildCpuInfo() {
        OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        String procLoad = String.format("%.2f %%", os.getProcessCpuLoad() * 100);
        String sysLoad = String.format("%.2f %%", os.getCpuLoad() * 100);
        return new CpuInfoDto(os.getAvailableProcessors(), os.getSystemLoadAverage(), procLoad, sysLoad);
    }

    private List<String> getActiveProfiles() {
        return Arrays.asList(environment.getActiveProfiles());
    }

    // bytes → MB 변환
    private static String formatMb(long bytes) {
        return String.format("%.2f MB", bytes / 1024.0 / 1024.0);
    }

    // bytes → GB 변환
    private static String formatGb(long bytes) {
        return String.format("%.2f GB", bytes / 1024.0 / 1024.0 / 1024.0);
    }

}
