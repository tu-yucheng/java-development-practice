package cn.tuyucheng.taketoday.boot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
}