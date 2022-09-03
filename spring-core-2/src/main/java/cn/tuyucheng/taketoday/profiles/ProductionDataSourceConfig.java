package cn.tuyucheng.taketoday.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class ProductionDataSourceConfig implements DataSourceConfig {

    @Override
    public void setup() {
        System.out.println("Setting up datasource for PRODUCTION environment. ");
    }
}