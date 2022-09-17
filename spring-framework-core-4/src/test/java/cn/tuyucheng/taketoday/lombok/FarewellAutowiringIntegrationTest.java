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
class FarewellAutowiringIntegrationTest {

    @Autowired
    private FarewellService farewellService;

    @Autowired
    private Translator translator;

    @Test
    void sayByeWithTranslatedMessage() {
        String translated = "translated";
        when(translator.translate("bye")).thenReturn(translated);
        assertEquals(translated, farewellService.farewell());
    }
}