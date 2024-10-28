package ddalpi.backend.controller;

import ddalpi.backend.dto.DefenseStatusDto;
import ddalpi.backend.service.DefenseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/defense_status")
public class DefenseController {

    private final DefenseInfoService service;

    @GetMapping
    public String getDefenceMethod(){
        String status = service.getStatus();
        return "message : " + status;
    }

    @PostMapping
    public String changeDefence(@RequestBody DefenseStatusDto dto){
        if("ip_sleep".equals(dto.defense_status())){
            service.updateStatus("ip_sleep");
        } else if ("vm_guard".equals(dto.defense_status())) {
            service.updateStatus("vm_guard");
        } else if ("load_balancing".equals(dto.defense_status())) {
            service.updateStatus("load_balancing");
        } else if ("open_wrt".equals(dto.defense_status())) {
            service.updateStatus("open_wrt");
        } else {
            service.updateStatus("none");
        }

        return "message : 수정 완료";
    }
}
