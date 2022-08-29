package cn.tuyucheng.taketoday.aware;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

public class MyBeanName implements BeanNameAware {
    private static final Logger log = LoggerFactory.getLogger(MyBeanName.class);

    @Override
    public void setBeanName(@NotNull String beanName) {
        log.info("beanName: {}", beanName);
        System.out.println(beanName);
    }
}