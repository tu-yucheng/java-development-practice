package cn.tuyucheng.taketoday.checkrolejava;

import cn.tuyucheng.taketoday.app.SecurityApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckRoleApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}