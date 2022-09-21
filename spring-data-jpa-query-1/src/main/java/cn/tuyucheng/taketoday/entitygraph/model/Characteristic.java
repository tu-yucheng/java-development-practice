package cn.tuyucheng.taketoday.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Characteristic {

    @Id
    private Long id;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Item item;
}