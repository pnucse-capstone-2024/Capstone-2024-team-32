package ddalpi.attacker.controller;

import ddalpi.attacker.dto.UrlRequest;
import ddalpi.attacker.service.AttackService;
import ddalpi.attacker.service.ImageService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class AttackController {
    private final AttackService attackService;
    private final ImageService imageService;

    public AttackController(AttackService attackService, ImageService imageService) {
        this.attackService = attackService;
        this.imageService = imageService;
    }

    @PostMapping("/start")
    @ResponseBody
    public String startAttack(@RequestBody UrlRequest urlRequest) {
        try {
            attackService.attackStart(urlRequest.url());
            return "공격이 종료됩니다.";
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/attack_status")
    @ResponseBody
    public String getAttackStatus() {
        if (attackService.isAttacking()) {
            return "작업이 아직 진행 중입니다.";
        } else {
            return "작업이 완료되었습니다. 버튼을 다시 활성화할 수 있습니다.";
        }
    }

    @GetMapping("/attack_image")
    @ResponseBody
    public Map<String, Integer> getStatus() {
        int image = imageService.getImage();

        Map<String, Integer> returnMap = new HashMap<>();
        return switch (image) {
            case 1 -> {
                returnMap.put("image", 1);
                yield returnMap;
            }
            case 2 -> {
                returnMap.put("image", 2);
                yield returnMap;
            }
            case 3 -> {
                returnMap.put("image", 3);
                yield returnMap;
            }
            case 4 -> {
                returnMap.put("image", 4);
                yield returnMap;
            }
            case 5 -> {
                returnMap.put("image", 5);
                yield returnMap;
            }
            default -> {
                returnMap.put("image", 1);
                yield returnMap;
            }
        };
    }

}