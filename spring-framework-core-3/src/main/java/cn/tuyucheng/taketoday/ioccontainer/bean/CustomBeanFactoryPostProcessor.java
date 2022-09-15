package cn.tuyucheng.taketoday.ioccontainer.bean;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static boolean isBeanFactoryPostProcessorRegistered = false;

    public static boolean isBeanFactoryPostProcessorRegistered() {
        return isBeanFactoryPostProcessorRegistered;
    }

    public static void setBeanFactoryPostProcessorRegistered(boolean isBeanFactoryPostProcessorRegistered) {
        CustomBeanFactoryPostProcessor.isBeanFactoryPostProcessorRegistered = isBeanFactoryPostProcessorRegistered;
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) {
        setBeanFactoryPostProcessorRegistered(true);
    }
}