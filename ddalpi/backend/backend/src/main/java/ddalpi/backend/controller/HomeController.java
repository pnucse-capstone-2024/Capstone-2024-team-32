package ddalpi.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String operation(){
        long a = 1L;

        for(long i = 0L; i < 2000000000L; i++){
            a += 1;
        }

        return "message : DDALPI";
    }
    @GetMapping("/home")
    public String home(){
        return "DDALPI";
    }
}
