package cn.tuyucheng.taketoday.customscope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TenantScopeIntegrationTest {

    @Test
    final void whenRegisterScopeAndBeans_thenContextContainsFooAndBar() {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
            ctx.register(TenantScopeConfig.class);
            ctx.register(TenantBeansConfig.class);
            ctx.refresh();

            TenantBean foo = ctx.getBean("foo", TenantBean.class);
            foo.sayHello();
            TenantBean bar = ctx.getBean("bar", TenantBean.class);
            bar.sayHello();
            Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);

            assertThat(foo, not(equalTo(bar)));
            assertThat(foos.size(), equalTo(2));
            assertTrue(foos.containsValue(foo));
            assertTrue(foos.containsValue(bar));

            BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
            BeanDefinition barDefinition = ctx.getBeanDefinition("bar");

            assertThat(fooDefinition.getScope(), equalTo("tenant"));
            assertThat(barDefinition.getScope(), equalTo("tenant"));
        }
    }

    @Test
    final void whenComponentScan_thenContextContainsFooAndBar() {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()) {
            ctx.scan("cn.tuyucheng.taketoday.customscope");
            ctx.refresh();

            TenantBean foo = ctx.getBean("foo", TenantBean.class);
            foo.sayHello();
            TenantBean bar = ctx.getBean("bar", TenantBean.class);
            bar.sayHello();
            Map<String, TenantBean> foos = ctx.getBeansOfType(TenantBean.class);

            assertThat(foo, not(equalTo(bar)));
            assertThat(foos.size(), equalTo(2));
            assertTrue(foos.containsValue(foo));
            assertTrue(foos.containsValue(bar));

            BeanDefinition fooDefinition = ctx.getBeanDefinition("foo");
            BeanDefinition barDefinition = ctx.getBeanDefinition("bar");

            assertThat(fooDefinition.getScope(), equalTo("tenant"));
            assertThat(barDefinition.getScope(), equalTo("tenant"));
        }
    }
}