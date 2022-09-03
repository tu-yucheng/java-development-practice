package cn.tuyucheng.taketoday.profiles;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SpringProfilesWithXMLIntegrationTest {

    private ClassPathXmlApplicationContext classPathXmlApplicationContext;

    @Test
    void testSpringProfilesForDevEnvironment() {
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springProfiles-config.xml");
        final ConfigurableEnvironment configurableEnvironment = classPathXmlApplicationContext.getEnvironment();
        configurableEnvironment.setActiveProfiles("dev");
        classPathXmlApplicationContext.refresh();
        final DataSourceConfig datasourceConfig = classPathXmlApplicationContext.getBean("devDatasourceConfig", DataSourceConfig.class);

        assertTrue(datasourceConfig instanceof DevDataSourceConfig);
    }

    @Test
    void testSpringProfilesForProdEnvironment() {
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:springProfiles-config.xml");
        final ConfigurableEnvironment configurableEnvironment = classPathXmlApplicationContext.getEnvironment();
        configurableEnvironment.setActiveProfiles("production");
        classPathXmlApplicationContext.refresh();
        final DataSourceConfig datasourceConfig = classPathXmlApplicationContext.getBean("productionDatasourceConfig", DataSourceConfig.class);

        assertTrue(datasourceConfig instanceof ProductionDataSourceConfig);
    }
}