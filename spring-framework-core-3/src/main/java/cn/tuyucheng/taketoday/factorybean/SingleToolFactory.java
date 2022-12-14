package cn.tuyucheng.taketoday.factorybean;

import lombok.Data;
import org.springframework.beans.factory.config.AbstractFactoryBean;

// no need to set singleton property because default value is true
@Data
public class SingleToolFactory extends AbstractFactoryBean<Tool> {
    private int factoryId;
    private int toolId;

    @Override
    public Class<?> getObjectType() {
        return Tool.class;
    }

    @Override
    protected Tool createInstance() {
        return new Tool(toolId);
    }
}