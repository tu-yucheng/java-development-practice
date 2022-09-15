package cn.tuyucheng.taketoday.factorybean;

import lombok.Data;
import org.springframework.beans.factory.config.AbstractFactoryBean;

@Data
public class NonSingleToolFactory extends AbstractFactoryBean<Tool> {
    private int factoryId;
    private int toolId;

    public NonSingleToolFactory() {
        setSingleton(false);
    }

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    protected Tool createInstance() {
        return new Tool(toolId);
    }
}