package cn.tuyucheng.taketoday.dynamicvalidation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfoExpression {
    @Id
    @Column(name = "expression_type")
    private String type;
    private String pattern;
}