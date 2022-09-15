package cn.tuyucheng.taketoday.ioccontainer.bean;

import org.springframework.beans.factory.config.BeanPostProcessor;

public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static boolean isBeanPostProcessorRegistered = false;

    public static boolean isBeanPostProcessorRegistered() {
        return isBeanPostProcessorRegistered;
    }

    public static void setBeanPostProcessorRegistered(boolean isBeanPostProcessorRegistered) {
        CustomBeanPostProcessor.isBeanPostProcessorRegistered = isBeanPostProcessorRegistered;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        setBeanPostProcessorRegistered(true);
        return bean;
    }
}