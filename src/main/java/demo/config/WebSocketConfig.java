package demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * websocket 配置项
 * Created by hasee on 2017/6/25.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * javascript
     * <code>
     * var sock = new SockJS('/portfolio')
     * </code>
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/portfolio").withSockJS();
    }

    /**
     * <code>
     * var client = Stomp.over(sock);
     * </code>
     * 设置订阅:
     * <code>
     * client.subscribe('/topic/greeting', function (event) { //TODO your codes }, {headers});
     * </code>
     * 发送消息：
     * <code>
     * client.send("/app/greeting", {headers}, JSON.stringify({json}))
     * </code>
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/topic", "/queue");
    }

}
