package ddalpi.attacker.httpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HttpPostClient {

    private URL url;
    private final Logger logger = Logger.getLogger(HttpPostClient.class.getName());

    public HttpPostClient(String path) {
        try {
            this.url = new URL(path);
        }
        catch (MalformedURLException e){
            logger.info(e.getMessage());
        }
    }

    public void sendResponseTimeToFastAPI(double averageResponseTime) {

        try {
            HttpURLConnection connection = getUrlConnection(averageResponseTime);

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
//            logger.info("Response Code: " + responseCode);

            // 응답 처리
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 성공적으로 요청이 처리된 경우, 응답 데이터를 읽을 수 있습니다.
                // 여기서는 생략했지만 필요에 따라 처리할 수 있습니다.
                logger.info("POST 요청이 성공적으로 전송되었습니다.");
            } else {
                logger.info("POST 요청 실패: " + responseCode);
            }

            connection.disconnect();
        }catch (IOException e){
            logger.info(e.getMessage());
        }

    }

    private HttpURLConnection getUrlConnection(double averageResponseTime) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{\"response_time\":" + averageResponseTime +" }";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        return connection;
    }
}
