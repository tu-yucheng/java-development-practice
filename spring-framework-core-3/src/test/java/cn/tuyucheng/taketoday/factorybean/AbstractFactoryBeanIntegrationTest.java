package cn.tuyucheng.taketoday.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:factorybean-abstract-spring-ctx.xml"})
class AbstractFactoryBeanIntegrationTest {

    @Resource(name = "singleTool")
    private Tool tool1;
    @Resource(name = "singleTool")
    private Tool tool2;
    @Resource(name = "nonSingleTool")
    private Tool tool3;
    @Resource(name = "nonSingleTool")
    private Tool tool4;

    @Test
    void testSingleToolFactory() {
        assertThat(tool1.getId(), equalTo(1));
        assertSame(tool1, tool2);
    }

    @Test
    void testNonSingleToolFactory() {
        assertThat(tool3.getId(), equalTo(2));
        assertThat(tool4.getId(), equalTo(2));
        assertNotSame(tool3, tool4);
    }
}