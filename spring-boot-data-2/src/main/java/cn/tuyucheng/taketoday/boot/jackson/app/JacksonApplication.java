package cn.tuyucheng.taketoday.boot.jackson.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "cn.tuyucheng.taketoday.boot.jackson.controller")
public class JacksonApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(JacksonApplication.class, args);
    }
}