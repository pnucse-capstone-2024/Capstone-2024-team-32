package ddalpi.backend.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpServletUtil {
    public static String getClientIp(HttpServletRequest request){
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = clientIp.split(",")[0].trim();
        }

        return clientIp;
    }
}
