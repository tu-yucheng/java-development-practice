## 1. 概述

随着Spring WebFlux的引入，我们可以编写响应式、非阻塞应用程序。
虽然现在使用这项技术比以前容易地多，但在Spring WebFlux中调试响应流可能非常麻烦。

在这个教程中，我们介绍如何轻松地以异步序列记录事件，以及如何避免一些简单的错误。

## 2. Gradle依赖

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
}
```

## 3. 创建响应流

首先，让我们使用Flux创建一个响应式流并使用log()方法启用日志记录：

```text
Flux<Integer> reactiveStream = Flux.range(1, 5).log();
```

接下来，我们将订阅它以消费生成的值：

```text
reactiveStream.subscribe();
```

## 4. 记录响应流

运行上述应用程序后，我们可以看到控制台记录的日志：

```text
16:13:29.735 [main] INFO reactor.Flux.Range.1 - | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
16:13:29.738 [main] INFO reactor.Flux.Range.1 - | request(unbounded)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onNext(1)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onNext(2)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onNext(3)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onNext(4)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onNext(5)
16:13:29.739 [main] INFO reactor.Flux.Range.1 - | onComplete()
```

从日志可以看到流中发生的每一个事件。首先发出五个值，然后通过onComplete()事件关闭流。

## 5. 高级日志记录场景

我们可以修改我们的应用程序以查看更有趣的场景。让我们将take()添加到Flux中，它将指示流仅提供特定数量的事件：

```text
Flux<Integer> reactiveStream = Flux.range(1, 5).log().take(3);
```

执行代码后，我们将看到以下输出：

```text
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | request(unbounded)
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | onNext(1)
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | onNext(2)
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | onNext(3)
16:13:29.741 [main] INFO reactor.Flux.Range.2 - | cancel()
```

正如我们所见，take()导致流在发出三个事件后取消。

**log()在流中的位置至关重要**。让我们看看将log()放在take()之后的效果：

```text
Flux<Integer> reactiveStream = Flux.range(1, 5).take(3).log();
```

并且输出：

```text
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | onSubscribe([Fuseable] FluxTake.TakeFuseableSubscriber)
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | request(unbounded)
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | onNext(1)
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | onNext(2)
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | onNext(3)
16:13:29.742 [main] INFO reactor.Flux.TakeFuseable.3 - | onComplete()
```

在上面，流生成了三个事件，但随后记录的不是cancel()，而是onComplete()。
**这是因为我们观察到使用take()的输出，而不是该方法所请求的输出**。

## 6. 总结

在这篇文章中，我们看到了如何使用内置的log()方法记录响应流。