package cn.tuyucheng.taketoday.dynamic.autowire;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DynamicAutowireConfig.class)
class DynamicAutowireIntegrationTest {

    @Autowired
    private BeanFactoryDynamicAutowireService beanFactoryDynamicAutowireService;

    @Autowired
    private CustomMapFromListDynamicAutowireService customMapFromListDynamicAutowireService;

    @Test
    void givenDynamicallyAutowiredBean_whenCheckingServerInGB_thenServerIsNotActive() {
        assertThat(beanFactoryDynamicAutowireService.isServerActive("CN", 101), is(false));
        assertThat(customMapFromListDynamicAutowireService.isServerActive("CN", 101), is(false));
    }

    @Test
    void givenDynamicallyAutowiredBean_whenCheckingServerInUS_thenServerIsActive() {
        assertThat(beanFactoryDynamicAutowireService.isServerActive("US", 101), is(true));
        assertThat(customMapFromListDynamicAutowireService.isServerActive("US", 101), is(true));
    }
}