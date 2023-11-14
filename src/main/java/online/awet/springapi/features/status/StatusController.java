package online.awet.springapi.features.status;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @RequestMapping
    public String getStatus() {
        return "{\"status\": \"OK\", \"time\": \"" + System.currentTimeMillis() + "\"}";
    }
}