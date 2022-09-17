package cn.tuyucheng.taketoday.lombok;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = TestConfig.class
)
class ThankingServiceAutowiringIntegrationTest {

    @Autowired
    private ThankingService thankingService;

    @Autowired
    private Translator translator;

    @Test
    void thankWithTranslatedMessage() {
        String translated = "translated";
        when(translator.translate("thank you")).thenReturn(translated);
        assertEquals(translated, thankingService.thank());
    }
}