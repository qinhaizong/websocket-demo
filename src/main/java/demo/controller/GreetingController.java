package demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试websocket功能
 * Created by hasee on 2017/6/26.
 */
@Controller
public class GreetingController {

    public static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    private SimpMessagingTemplate template;

    @Autowired
    public GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/greeting")
    public String handle(String greeting) {
        LOGGER.info("greeting: {}", greeting);
        return new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(new Date());
    }
}
