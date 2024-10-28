package ddalpi.backend.service;

import ddalpi.backend.domain.DefenseInfo;
import ddalpi.backend.repository.DefenseInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefenseInfoService {
    private final DefenseInfoRepository repository;
    public String getStatus(){
        DefenseInfo defenseInfo = repository.findById(1L).orElseThrow();
        return defenseInfo.getStatus();
    }
    @Transactional
    public void updateStatus(String status){
        DefenseInfo defenseInfo = repository.findById(1L).orElseThrow();
        defenseInfo.updateStatus(status);
    }

}
