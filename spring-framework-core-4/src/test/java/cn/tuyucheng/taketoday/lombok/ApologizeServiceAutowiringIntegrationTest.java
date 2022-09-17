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
class ApologizeServiceAutowiringIntegrationTest {
    private final static String TRANSLATED = "TRANSLATED";

    @Autowired
    private ApologizeService apologizeService;

    @Autowired
    private Translator translator;

    @Test
    void apologizeWithTranslatedMessage() {
        when(translator.translate("sorry")).thenReturn(TRANSLATED);
        assertEquals(TRANSLATED, apologizeService.apologize());
    }
}