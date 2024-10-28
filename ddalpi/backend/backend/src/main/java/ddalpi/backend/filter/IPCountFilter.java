package ddalpi.backend.filter;

import static ddalpi.backend.function.SigmoidFunction.sigmoid;
import static ddalpi.backend.utils.HttpServletUtil.getClientIp;

import ddalpi.backend.domain.RequestIP;
import ddalpi.backend.service.DefenseInfoService;
import ddalpi.backend.service.RequestIPService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class IPCountFilter implements Filter {

    public static final String REDIRECT_URL = "http://54.180.139.115:8080";
    public static final String VM_URL = "http://172.20.10.14:8080/ip_guard";

    private final RequestIPService service;
    private final DefenseInfoService infoService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isUrlInWhiteList(httpRequest.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(httpRequest);

        RequestIP requestIP = service.updateIp(clientIp);
        String status = infoService.getStatus();

        switch (status){
            case "ip_sleep" -> {
                log.info("IP_SLEEP 작동 중");

                double v = sigmoid(requestIP.getCount()) * 1000;

                int sleepTime = (int) v;

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case "vm_guard" -> {
                log.info("VM 방화벽 작동 중");
                double v = sigmoid(requestIP.getCount());

                Random random = new Random();

                double randomValue = random.nextDouble();

                if (randomValue < v){
                    reportAttacker(requestIP.getIp());
                }
            }
            case "load_balancing" -> {
                log.info("LOAD BALANCING 작동 중");
                double v = sigmoid(requestIP.getCount());

                Random random = new Random();

                double randomValue = random.nextDouble();

                if (randomValue < v){
                    ((HttpServletResponse) response).sendRedirect(REDIRECT_URL);
                }
            }
            case "open_wrt" -> {
                log.info("OPEN_WRT 작동 중");
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isUrlInWhiteList(String path){
        return path.startsWith("/h2-console");
    }

    private void reportAttacker(String ip) {
        try {
            URL url = new URL(VM_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 요청 방식 설정 - POST
            conn.setRequestMethod("POST");

            // Content-Type 헤더 설정 (JSON 형식)
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");

            // 출력 가능하도록 설정
            conn.setDoOutput(true);

            // 보낼 JSON 데이터
            String jsonInputString = "{\"attacker_ip\":\"" + ip + "\"}";

            // JSON 데이터 전송
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
