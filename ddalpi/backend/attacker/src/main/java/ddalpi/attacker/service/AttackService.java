package ddalpi.attacker.service;

import ddalpi.attacker.httpRequest.HttpConcurrentClient;
import ddalpi.attacker.httpRequest.HttpPostClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AttackService {
    private final String GRAPH_URL = "http://localhost:7000/response_time";
    private final int REPEAT_NUMBER = 40;
    public final int SLEEP_TIME = 240 * 1000;
    public boolean attacking = false;
    private final ImageService imageService;

    public AttackService(ImageService imageService) {
        this.imageService = imageService;
    }

    @Async
    public void attackStart(String url) throws ExecutionException, InterruptedException{
        //인스턴스 생성
        Logger logger = Logger.getLogger(AttackService.class.getName());
        HttpConcurrentClient client = new HttpConcurrentClient();
        HttpPostClient postClient = new HttpPostClient(GRAPH_URL);

        logger.info("공격을 수행합니다.");
        List<Double> averageResponseTimes = new ArrayList<>();
        List<Double> averageAverageResponseTimes = new ArrayList<>();

        int realStop = 0;
        int stopCount = 0;
        int decreaseCount = 0;

        attacking = true;

        while(realStop < 2) {
            imageService.setImage(2);

            for (int i = 0; i < REPEAT_NUMBER; i++) {
                double averageResponseTime = client.getAverageResponseTime(url);
                averageResponseTimes.add(averageResponseTime);
                postClient.sendResponseTimeToFastAPI(averageResponseTime);
            }

            double sum = calculateAverageResponseTime(averageResponseTimes);

            averageAverageResponseTimes.add(sum / REPEAT_NUMBER);

            if(averageAverageResponseTimes.size() >= 2){
                if (!isDecreaseResponseTime(averageAverageResponseTimes)) {
                    stopCount += 1;
                }else{
                    decreaseCount += 1;
                }
            }

            if (stopCount >= 2) {
                logger.info("공격 유효하지 않음. Scale In을 유도합니다.");
                averageResponseTimes = new ArrayList<>();
                averageAverageResponseTimes = new ArrayList<>();
                stopCount = 0;

                if(decreaseCount == 0){
                    realStop += 1;
                }

                decreaseCount = 0;

                if(realStop >= 2){
                    imageService.setImage(5);
                }
                else if(realStop > 0){
                    imageService.setImage(4);
                }else{
                    imageService.setImage(3);
                }

                logger.info("realStop = "+realStop);

                Thread.sleep(SLEEP_TIME);
            }
        }

        logger.info("공격 종료");
        imageService.setImage(5);
        attacking = false;
    }

    private boolean isDecreaseResponseTime(List<Double> averageAverageResponseTimes) {
        return (averageAverageResponseTimes.get(averageAverageResponseTimes.size() - 2) - averageAverageResponseTimes.getLast() > 100);
    }

    private double calculateAverageResponseTime(List<Double> averageResponseTimes) {
        double sum = 0;

        for(int i = 1; i < REPEAT_NUMBER - 1; i++){
            int position = averageResponseTimes.size() - i;
            sum += averageResponseTimes.get(position);
        }
        return sum;
    }

    public boolean isAttacking() {
        return attacking;
    }
}


