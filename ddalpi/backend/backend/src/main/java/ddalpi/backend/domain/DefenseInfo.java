package ddalpi.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DefenseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    protected DefenseInfo() {
    }

    public String getStatus() {
        return status;
    }

    public void updateStatus(String status){
        this.status = status;
    }
}
