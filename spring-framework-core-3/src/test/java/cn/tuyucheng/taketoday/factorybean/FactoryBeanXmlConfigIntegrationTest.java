package cn.tuyucheng.taketoday.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:factorybean-spring-ctx.xml"})
class FactoryBeanXmlConfigIntegrationTest {
    @Autowired
    private Tool tool;
    @Resource(name = "&tool")
    private ToolFactory toolFactory;

    @Test
    void testConstructWorkerByXml() {
        assertThat(tool.getId(), equalTo(1));
        assertThat(toolFactory.getFactoryId(), equalTo(9090));
    }
}