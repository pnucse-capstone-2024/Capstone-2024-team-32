package ddalpi.backend.service;

import ddalpi.backend.domain.RequestIP;
import ddalpi.backend.repository.RequestIPRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestIPService {

    private final RequestIPRepository repository;
    @Transactional
    public RequestIP updateIp(String ip){
        Optional<RequestIP> requestIP = repository.findByIpForUpdate(ip);

        if(requestIP.isPresent()){
            RequestIP nowIp = requestIP.get();

            nowIp.modifyCount();

            return nowIp;
        }
        else{
            RequestIP newIp = new RequestIP(ip);
            repository.save(newIp);
            return newIp;
        }
    }
}
