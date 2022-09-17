package cn.tuyucheng.taketoday.customannotation;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 7857541629844398625L;

    private Long id;
    private String email;
    private Person person;
}