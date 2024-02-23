package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public interface OrderControllerV1 {

    @GetMapping("/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/no-log")
    String noLog();
}
