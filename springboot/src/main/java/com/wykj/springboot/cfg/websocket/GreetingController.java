package com.wykj.springboot.cfg.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    /*
     *  @MessageMapping("/hello")注解的方法将用来接收
     * ”/app/hello“路径发送来的消息，在注解方法中对消息进行处理后，
     * 再将消息转发到@SendTo定义的路径上，而@SendTo路径
     * 是一个前缀为“topic”的路径，因此该消息将被交给消息代理broker
     * 再由broker进行广播
     *
     *  访问 {@link http://localhost:9999/chat} 体验websockt
     * */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Message greeting(Message message) {
        return message;
    }
}
