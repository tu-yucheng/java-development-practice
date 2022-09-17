## 1. 概述

在本教程中，我们将深入探讨Java反应式编程，以解决如何将Flux<DataBuffer\>读入单个InputStream这个问题。

## 2. 需求

作为解决将Flux<DataBuffer\>读入单个InputStream问题的第一步，我们将使用Spring响应式WebClient发出GET请求。
此外，我们可以将gorest.co.in托管的公共API端点之一用于此类测试场景：

```text
String REQUEST_ENDPOINT = "https://gorest.co.in/public/v2/users";
```

接下来，让我们定义用于获取WebClient实例的getWebClient()方法：

```java
public class DataBufferToInputStream {

    private static WebClient getWebClient() {
        WebClient.Builder webClientBuilder = WebClient.builder();
        return webClientBuilder.build();
    }
}
```

现在，我们可以向/public/v2/users端点发出GET请求。
但是，我们必须将响应主体作为Flux<DataBuffer\>对象。

## 3. BodyExtractor和DataBufferUtils

**我们可以使用spring-webflux中BodyExtractors类的toDataBuffers()方法将响应体提取到Flux<DataBuffer\>中**。

让我们继续创建body作为Flux<DataBuffer\>类型的实例：

```text
Flux<DataBuffer> body = client.get()
        .uri(url)
        .exchangeToFlux(clientResponse -> {
            return clientResponse.body(BodyExtractors.toDataBuffers());
        });
```

接下来，由于我们需要将这些DataBuffer流收集到单个InputStream中，实现此目的的一个好策略是使用PipedInputStream和PipedOutputStream。

此外，我们打算写入PipedOutputStream并最终从PipedInputStream读取。因此，让我们看看如何创建这两个连接的流：

```text
PipedOutputStream outputStream = new PipedOutputStream();
PipedInputStream inputStream = new PipedInputStream(1024*10);
inputStream.connect(outputStream);
```

我们必须注意，默认大小是1024字节。但是，我们预计从Flux<DataBuffer\>收集的结果可能会超过默认值。
因此，我们需要明确指定一个更大的值，在本例中为1024*10。

最后，我们使用DataBufferUtils类中可用的write()工具方法将body作为发布者写入outputStream：

```text
DataBufferUtils.write(body, outputStream).subscribe();
```

我们必须注意，我们在声明时连接了inputStream和outputStream。

## 4. 从PipedInputStream读取

首先，让我们定义一个工具方法readContent()，将InputStream作为String对象读取：

```text
private static String readContent(InputStream stream) throws IOException {
    StringBuilder contentStringBuffer = new StringBuilder();
    byte[] tmp = new byte[stream.available()];
    int byteCount = stream.read(tmp, 0, tmp.length);
    logger.info(String.format("read %d bytes from the stream\n", byteCount));
    contentStringBuffer.append(new String(tmp));
    return String.valueOf(contentStringBuffer);
}
```

接下来，因为在不同的线程中读取PipedInputStream是一种典型做法，所以让我们创建readContentFromPipedInputStream()方法，
该方法在内部生成一个新线程，通过调用readContent()方法将PipedInputStream中的内容读取到String对象中：

```text
private static String readContentFromPipedInputStream(PipedInputStream stream) throws IOException {
    StringBuffer contentStringBuffer = new StringBuffer();
    try (stream) {
        Thread pipeReader = new Thread(() -> {
            try {
                contentStringBuffer.append(readContent(stream));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        pipeReader.start();
        pipeReader.join();
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }
    return String.valueOf(contentStringBuffer);
}
```

到目前为止，我们就可以开始测试了，让我们看看它的实际效果：

```text
public static void main(String[] args) throws IOException, InterruptedException {
    WebClient webClient = getWebClient();
    InputStream inputStream = getResponseAsInputStream(webClient, REQUEST_ENDPOINT);
    Thread.sleep(3000);
    String content = readContentFromPipedInputStream((PipedInputStream) inputStream);
    logger.info("response content: \n{}", content.replace("}", "}\n"));
}
```

当我们处理异步系统时，我们在读取流之前将读取延迟任意3秒，以便我们能够看到完整的响应。
此外，在日志记录时，我们插入换行符，将长输出分成多行。

最后，让我们验证一下代码执行生成的输出：

```text
16:44:04.120 [main] INFO cn.tuyucheng.taketoday.databuffer.DataBufferToInputStream - response content: 
[{"id":2642,"name":"Bhupen Trivedi","email":"bhupen_trivedi@renner-pagac.name","gender":"male","status":"active"}
,{"id":2637,"name":"Preity Patel","email":"patel_preity@abshire.info","gender":"female","status":"inactive"}
,{"id":2633,"name":"Brijesh Shah","email":"brijesh_shah@morar.co","gender":"male","status":"inactive"}
...
,{"id":2623,"name":"Mohini Mishra","email":"mishra_mohini@hamill-ledner.info","gender":"female","status":"inactive"}
]
```

## 5. 总结

在本文中，我们使用管道流的概念以及BodyExtractors和DataBufferUtils类中可用的工具方法将Flux<DataBuffer\>读入单个InputStream。