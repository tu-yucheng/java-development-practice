package cn.tuyucheng.taketoday.customannotation;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 9005331414216374586L;

    private Long id;
    private String name;
}