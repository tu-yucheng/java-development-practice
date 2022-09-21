package cn.tuyucheng.taketoday.aggregation.model.custom;

public interface CommentCountProjection {

    Integer getYearComment();

    Long getTotalComment();
}