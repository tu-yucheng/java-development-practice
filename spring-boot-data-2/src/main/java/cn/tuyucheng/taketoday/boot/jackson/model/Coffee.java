package cn.tuyucheng.taketoday.boot.jackson.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Coffee {
    private String name;
    private String brand;
    private LocalDateTime date;
}