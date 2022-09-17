## 1. 概述

有时，我们必须在Web应用程序中提供静态资源。它可能是图像、HTML、CSS或JavaScript文件。

在本教程中，我们将演示如何使用Spring WebFlux提供静态内容。

## 2. 覆盖默认配置

默认情况下，Spring Boot从以下位置提供静态资源：

+ /public
+ /static
+ /resources
+ /META-INF/resources

这些路径中的所有文件都在/resource-file-name路径下提供。

如果我们想更改Spring WebFlux的默认路径，我们需要将此属性添加到我们的application.properties文件中：

```properties
# Use in Static content Example
spring.webflux.static-path-pattern=/assets/**
```

现在，静态资源将位于/assets/resource-file-name下。

**请注意，当存在@EnableWebFlux注解时，这将不起作用**。

## 3. 路由案例

也可以使用WebFlux路由机制来提供静态资源。

让我们看一个为index.html文件提供服务的路由定义示例：

```java

@Configuration
public class StaticContentConfig {

    @Bean
    public RouterFunction<ServerResponse> htmlRouter(@Value("classpath:/public/index.html") Resource html) {
        return route(GET("/"), req -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html));
    }
}
```

我们还可以借助RouterFunction从自定义位置提供静态资源。

让我们看看如何使用/img/**路径从src/main/resources/img目录提供图像：

```java

@Configuration
public class StaticContentConfig {

    @Bean
    public RouterFunction<ServerResponse> imgRouter() {
        return resources("/img/**", new ClassPathResource("img/"));
    }
}
```

## 4. 自定义Web资源路径示例

另一种提供存储在自定义位置的静态文件的方法，而不是默认的src/main/resources路径，
是使用maven-resources-plugin和一个额外的Spring WebFlux属性。

首先，让我们将插件添加到我们的pom.xml中：

```text
<plugin>
    <artifactId>maven-resources-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>copy-resources</id>
            <phase>validate</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <resources>
                    <resource>
                        <directory>src/main/assets</directory>
                        <filtering>true</filtering>
                    </resource>
                </resources>
                <outputDirectory>${basedir}/target/classes/assets</outputDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

然后，我们只需要设置static-locations属性：

```properties
spring.resources.static-locations=classpath:/assets/
```

在定义这些之后，index.html将在http://localhost:8080/index.html URL下可用。

## 5. 总结

在本文中，我们学习了如何在Spring WebFlux中提供静态资源。