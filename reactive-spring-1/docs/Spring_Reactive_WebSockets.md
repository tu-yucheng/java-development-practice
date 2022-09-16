## 1. 概述

在本文中，我们将使用新的Spring 5 WebSockets API以及Spring WebFlux提供的响应式特性创建一个案例程序。

WebSocket是一种众所周知的协议，可以实现客户端和服务器之间的全双工通信，通常用于客户端和服务器需要以高频率和低延迟交换事件的Web应用程序。

Spring 5对框架中的WebSockets支持进行了现代化改造，为该通信通道添加了响应式功能。

## 2. Maven依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-integration</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

## 3. Spring中的WebSocket配置

我们的配置非常简单：只需要注入WebSocketHandler来处理Spring WebSocket应用程序中的套接字会话。

```java

@Configuration
public class ReactiveWebSocketConfiguration {

    @Autowired
    private WebSocketHandler webSocketHandler;
}
```

此外，让我们创建一个返回HandlerMapping bean的方法，该方法将负责请求和处理程序对象之间的映射：

```java
public class ReactiveWebSocketConfiguration {

    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/event-emitter", webSocketHandler);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1);
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }
}
```

我们可以访问的URL是：ws://localhost:<port\>/event-emitter。

## 4. Spring中的WebSocket消息处理

我们的ReactiveWebSocketHandler类将负责管理服务器端的WebSocket会话。

它实现了WebSocketHandler接口，因此我们可以重写handle方法，该方法将用于将消息发送到WebSocket客户端：

```java

@Component("ReactiveWebSocketHandler")
public class ReactiveWebSocketHandler implements WebSocketHandler {

    private static final ObjectMapper json = new ObjectMapper();

    private final Flux<String> eventFlux = Flux.generate(sink -> {
        Event event = new Event(randomUUID().toString(), now().toString());
        try {
            sink.next(json.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            sink.error(e);
        }
    });

    private final Flux<String> intervalFlux = Flux.interval(Duration.ofMillis(1000L))
            .zipWith(eventFlux, (time, event) -> event);

    @Override
    public @NotNull Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(intervalFlux.map(webSocketSession::textMessage))
                .and(webSocketSession.receive()
                        .map(WebSocketMessage::getPayloadAsText).log());
    }
}
```

## 5. 创建一个简单的响应式WebSocket客户端

现在让我们创建一个Spring Reactive WebSocket客户端，它将能够与我们的WebSocket服务器连接并交换信息。

### 5.1 WebSocket客户端

现在，让我们创建ReactiveClientWebSocket类，负责启动与服务器的通信：

```java
public class ReactiveJavaClientWebSocket {

    public static void main(String[] args) throws InterruptedException {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create("ws://localhost:8080/event-emitter"),
                        session -> session.send(
                                        Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                                .thenMany(session.receive()
                                        .map(WebSocketMessage::getPayloadAsText)
                                        .log())
                                .then())
                .block(Duration.ofSeconds(10L));
    }
}
```

在上面的代码中，我们可以看到我们使用的是ReactorNettyWebSocketClient，它是与Reactor Netty一起使用的WebSocketClient实现。

此外，客户端通过URL ws://localhost:8080/event-emitter连接到WebSocket服务器，并在连接到服务器后立即建立会话。

我们还可以看到，我们向服务器发送一条消息(“event-spring-reactive-client-websocket”)以及连接请求。

此外，调用send方法，将Publisher<T\>类型的变量作为参数，在我们的例子中，Publisher<T\>是Mono<T\>，
T是一个简单的字符串“event-me-from-reactive-java-client-websocket“。

然后，调用了期望类型为String的Flux的thenMany(…)方法。receive()方法获取传入消息的Flux，然后将其转换为字符串。

最后，block()方法强制客户端在给定时间(在我们的示例中为10秒)后与服务器断开连接。

### 5.2 启动客户端

要运行它，请首先确保响应式WebSocket服务器已启动并正在运行。
然后，启动ReactiveJavaClientWebSocket类，我们可以在日志中看到正在发出的事件：

```text
[reactor-http-nio-4] INFO reactor.Flux.Map.1 - 
onNext({"eventId":"6042b94f-fd02-47a1-911d-dacf97f12ba6",
"eventDt":"2022-09-16T21:29:26.900"})
```

我们还可以在Reactive WebSocket服务器的日志中看到客户端在连接尝试期间发送的消息：

```text
[reactor-http-nio-2] reactor.Flux.Map.1: 
onNext(event-me-from-reactive-java-client)
```

此外，我们可以在客户端完成其请求后看到终止连接的消息(在我们的例子中，为10秒之后)：

```text
[reactor-http-nio-2] reactor.Flux.Map.1: onComplete()
```

## 6. 浏览器WebSocket客户端

让我们创建一个简单的HTML/Javascript客户端WebSocket来使用我们的响应式WebSocket服务器应用程序。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>tuyucheng: Spring 5 Reactive Client WebSocket (Browser)</title>
</head>
<body>

<div class="events"></div>
<script>
    var clientWebSocket = new WebSocket("ws://localhost:8080/event-emitter");
    clientWebSocket.onopen = function () {
        console.log("clientWebSocket.onopen", clientWebSocket);
        console.log("clientWebSocket.readyState", "websocketstatus");
        clientWebSocket.send("event-me-from-browser");
    }
    clientWebSocket.onclose = function (error) {
        console.log("clientWebSocket.onclose", clientWebSocket, error);
        events("Closing connection");
    }
    clientWebSocket.onerror = function (error) {
        console.log("clientWebSocket.onerror", clientWebSocket, error);
        events("An error occured");
    }
    clientWebSocket.onmessage = function (error) {
        console.log("clientWebSocket.onmessage", clientWebSocket, error);
        events(error.data);
    }

    function events(responseEvent) {
        document.querySelector(".events").innerHTML += responseEvent + "<br>";
    }
</script>
</body>
</html>
```

随着WebSocket服务器运行，在浏览器中打开这个HTML文件(例如：Chrome、Internet Explorer、Mozilla Firefox等)，
我们应该看到正在屏幕上打印的事件，每个事件延迟1秒。

```text
{"eventId":"c25975de-6775-4b0b-b974-b396847878e6","eventDt":"2022-09-16T21:56:09.780"}
{"eventId":"ac74170b-1f71-49d3-8737-b3f9a8a352f9","eventDt":"2022-09-16T21:56:09.781"}
{"eventId":"40d8f305-f252-4c14-86d7-ed134d3e10c6","eventDt":"2022-09-16T21:56:09.782"}
```

## 7. 总结

在本文中，我们编写了一个示例，说明如何使用Spring 5框架在服务器和客户端之间创建WebSocket通信，实现Spring Webflux提供的响应式特性。