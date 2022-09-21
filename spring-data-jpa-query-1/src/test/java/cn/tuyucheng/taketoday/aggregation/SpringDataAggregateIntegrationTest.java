package cn.tuyucheng.taketoday.aggregation;

import cn.tuyucheng.taketoday.aggregation.model.custom.CommentCount;
import cn.tuyucheng.taketoday.aggregation.model.custom.CommentCountProjection;
import cn.tuyucheng.taketoday.aggregation.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
@Sql(scripts = "/test-aggregation-data.sql")
class SpringDataAggregateIntegrationTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void whenQueryWithAggregation_thenReturnResult() {
        List<Object[]> commentCountsByYear = commentRepository.countTotalCommentsByYear();

        Object[] countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019[0], is(2019));
        assertThat(countYear2019[1], is(1L));

        Object[] countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018[0], is(2018));
        assertThat(countYear2018[1], is(2L));

        Object[] countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017[0], is(2017));
        assertThat(countYear2017[1], is(1L));
    }

    @Test
    void whenQueryWithAggregation_thenReturnCustomResult() {
        List<CommentCount> commentCountsByYear = commentRepository.countTotalCommentsByYearClass();

        CommentCount countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYear(), is(2019));
        assertThat(countYear2019.getTotal(), is(1L));

        CommentCount countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYear(), is(2018));
        assertThat(countYear2018.getTotal(), is(2L));

        CommentCount countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYear(), is(2017));
        assertThat(countYear2017.getTotal(), is(1L));
    }

    @Test
    void whenQueryWithAggregation_thenReturnInterfaceResult() {
        List<CommentCountProjection> commentCountsByYear = commentRepository.countTotalCommentsByYearInterface();

        CommentCountProjection countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYearComment(), is(2019));
        assertThat(countYear2019.getTotalComment(), is(1L));

        CommentCountProjection countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYearComment(), is(2018));
        assertThat(countYear2018.getTotalComment(), is(2L));

        CommentCountProjection countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYearComment(), is(2017));
        assertThat(countYear2017.getTotalComment(), is(1L));
    }

    @Test
    void whenNativeQueryWithAggregation_thenReturnInterfaceResult() {
        List<CommentCountProjection> commentCountsByYear = commentRepository.countTotalCommentsByYearNative();

        CommentCountProjection countYear2019 = commentCountsByYear.get(0);

        assertThat(countYear2019.getYearComment(), is(2019));
        assertThat(countYear2019.getTotalComment(), is(1L));

        CommentCountProjection countYear2018 = commentCountsByYear.get(1);

        assertThat(countYear2018.getYearComment(), is(2018));
        assertThat(countYear2018.getTotalComment(), is(2L));

        CommentCountProjection countYear2017 = commentCountsByYear.get(2);

        assertThat(countYear2017.getYearComment(), is(2017));
        assertThat(countYear2017.getTotalComment(), is(1L));
    }
}