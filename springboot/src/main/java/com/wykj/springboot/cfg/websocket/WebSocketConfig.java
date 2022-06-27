package com.wykj.springboot.cfg.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 自定义类WebSocketConfig继承WebSocketMessageBrokerConfigurer
 * 进行WebSocket配置
 * 通过@EnableWebSocketMessageBroker注解开启WebSocket消息代理
 */
@Configuration
//注解开启使用STOMP协议来传输基于代理(message broker)的消息
// 这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样

@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //配置消息代理(Message Broker)
     public void configureMessageBroker(MessageBrokerRegistry registry) {
         /*
         *设置消息代理前缀，即如果消息的前缀是“/topic”,就会将消息转发给消息代理（broker）,
         *再由消息代理将消息广播给当前连接的客户端。（topic路径交给broker处理）
         * */
         //点对点应配置一个/user消息代理，广播式应配置一个/topic消息代理
         registry.enableSimpleBroker("/topic");
         /*表示配置一个或者多个前缀，通过这些前缀过滤出需要被注解方法处理的消息
         * 例如，前缀为“/app”的destination可以通过@MessageMapping注解的方法处理
         * 而其他destination（例如“/topic” “/queue”）将被直接交给broker处理
         * （app路径交给@MessageMapping注解的方法处理）
         * */
         //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         registry.setApplicationDestinationPrefixes("/app");
    }

    //注册STOMP协议的节点(endpoint),并映射指定的url
     public void registerStompEndpoints(StompEndpointRegistry registry) {
         /*表示定义一个前缀为"/chat"的endPoint,并开启sockjs支持
         * sockjs可以解决浏览器对WebSocket的兼容性问题，客户端将通过
         * 这里配置的URL建立WebSocket连接
         * （）
         * */
         //注册一个STOMP的endpoint,并指定使用SockJS协议
         registry.addEndpoint("/chat").withSockJS();
    }
}