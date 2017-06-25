package demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试spring mvc功能
 * Created by hasee on 2017/6/25.
 */
@RestController
public class HelloController {

    @GetMapping("hi")
    public String hello(@RequestParam("name") String name) {
        return "Hello " + name;
    }
}
