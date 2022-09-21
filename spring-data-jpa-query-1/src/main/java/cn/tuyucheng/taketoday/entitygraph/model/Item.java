package cn.tuyucheng.taketoday.entitygraph.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NamedEntityGraph(
        name = "Item.characteristics",
        attributeNodes = @NamedAttributeNode("characteristics")
)
public class Item {

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "item")
    private List<Characteristic> characteristics = new ArrayList<>();
}