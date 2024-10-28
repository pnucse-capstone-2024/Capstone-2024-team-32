package ddalpi.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_ip")
public class RequestIP extends TimeStamp{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String ip;
    private Integer count;
    protected RequestIP() {
    }
    public RequestIP(String ip) {
        this.ip = ip;
        this.count = 1;
    }
    public void addCount(){
        this.count += 1;
    }
    public void modifyCount(){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(this.getUpdatedDate(), now);

        if (duration.toMinutes() > 10) {
            // 시간이 10분 이내이면 count 값을 0으로 설정합니다.
            this.count = 0;
        } else {
            // 그렇지 않으면 count 값을 1 증가시킵니다.
            this.count++;
        }
    }
    public Integer getCount() {
        return count;
    }
    public String getIp() {
        return ip;
    }
}
