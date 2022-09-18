package cn.tuyucheng.taketoday.boot.jackson.app;

import cn.tuyucheng.taketoday.boot.jackson.config.CoffeeHttpConverterConfiguration;
import org.springframework.context.annotation.Import;

@Import(CoffeeHttpConverterConfiguration.class)
class CoffeeHttpConverterIntegrationTest extends AbstractCoffeeIntegrationTest {
    
}