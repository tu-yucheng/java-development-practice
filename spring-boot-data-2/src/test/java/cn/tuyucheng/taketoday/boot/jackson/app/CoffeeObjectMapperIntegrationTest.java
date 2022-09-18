package cn.tuyucheng.taketoday.boot.jackson.app;

import cn.tuyucheng.taketoday.boot.jackson.config.CoffeeObjectMapperConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeObjectMapperConfig.class)
class CoffeeObjectMapperIntegrationTest extends AbstractCoffeeIntegrationTest {
    
}