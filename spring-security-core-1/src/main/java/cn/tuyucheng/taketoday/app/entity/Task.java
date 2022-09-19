package cn.tuyucheng.taketoday.app.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String assignee;

    public Task() {
    }

    public Task(String description, String assignee) {
        this.description = description;
        this.assignee = assignee;
    }
}