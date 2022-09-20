package cn.tuyucheng.taketoday.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Setter
@Getter
public class Fruit {

    @Id
    private long id;
    private String name;
    private String color;
}