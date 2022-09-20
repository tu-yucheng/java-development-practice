package cn.tuyucheng.taketoday.datajpadelete.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Setter
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @ManyToOne
    private Category category;

    public Book() {
    }

    public Book(String title) {
        this.title = title;
    }

    public Book(String title, Category category) {
        this.title = title;
        this.category = category;
    }
}