package cn.tuyucheng.taketoday.aggregation.model.custom;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentCount {
    private Integer year;
    private Long total;
}