package cn.tuyucheng.taketoday.boot.jackson.app;

import cn.tuyucheng.taketoday.boot.jackson.config.CoffeeRegisterModuleConfig;
import org.springframework.context.annotation.Import;

@Import(CoffeeRegisterModuleConfig.class)
class CoffeeRegisterModuleIntegrationTest extends AbstractCoffeeIntegrationTest {

}