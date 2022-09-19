package cn.tuyucheng.taketoday.dynamicvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.security.RolesAllowed;

@SpringBootApplication
public class DynamicValidationApplication {
    
    @RolesAllowed("*")
    public static void main(String[] args) {
        SpringApplication.run(DynamicValidationApplication.class, args);
    }
}