package client;

import org.junit.Test;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hasee on 2017/6/26.
 */
public class AppTest {

    /**
     * TODO unfinished
     */
    @Test
    public void testSockJs() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        SockJsClient client = new SockJsClient(transports);
        WebSocketHandler handler = new TextWebSocketHandler() {
            @Override
            protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
                ByteBuffer buffer = message.getPayload();
                try {
                    String s = new String(buffer.array(), "UTF-8");
                    System.out.println(s);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };
        client.doHandshake(handler, "ws://localhost:9090/portfolio");
    }

    /**
     * connect to service from this project.
     *
     * @throws InterruptedException
     */
    @Test
    public void testStomp() throws InterruptedException {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());
        WebSocketStompClient client = new WebSocketStompClient(new SockJsClient(transports));
        client.setMessageConverter(new StringMessageConverter());
        StompSessionHandlerAdapter handler = new StompSessionHandlerAdapter() {
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                System.out.println(payload.toString());
            }

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }
        };
        ListenableFutureCallback<StompSession> listenableFutureCallback = new ListenableFutureCallback<StompSession>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(StompSession stompSession) {
                stompSession.subscribe("/topic/greeting", handler);
                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        String datetime = Instant.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_DATE_TIME);
                        stompSession.send("/app/greeting", "hello world @" + datetime);
                    }
                }, 0, 1L, TimeUnit.MINUTES);
            }
        };
        client.connect("ws://localhost:9090/portfolio", handler).addCallback(listenableFutureCallback);
        Thread.sleep(30 * 60 * 1000L);
    }

    /**
     * Instant 它代表的是时间戳，比如2014-01-14T02:20:13.592Z
     */
    @Test
    public void j8Instant() {
        Instant now = Instant.now();
        System.out.println(now);
        Instant instant = Instant.now(Clock.systemDefaultZone());
        System.out.println(instant);
        System.out.println(Instant.now(Clock.system(ZoneId.of("Asia/Shanghai"))));
    }

    /**
     * 不可变且线程安全
     */
    @Test
    public void j8DatetimeFormater() {
        String s = Instant.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ISO_DATE);
        System.out.println(s);
        System.out.println(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
