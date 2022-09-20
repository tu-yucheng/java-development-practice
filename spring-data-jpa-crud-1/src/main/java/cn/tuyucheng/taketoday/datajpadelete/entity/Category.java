package cn.tuyucheng.taketoday.datajpadelete.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Book... books) {
        this.name = name;
        this.books = Stream.of(books).collect(Collectors.toList());
        this.books.forEach(x -> x.setCategory(this));
    }

    public Category(String name, List<Book> books) {
        this.name = name;
        this.books = books;
    }
}