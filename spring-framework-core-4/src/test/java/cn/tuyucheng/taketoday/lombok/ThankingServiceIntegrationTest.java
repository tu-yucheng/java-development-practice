package cn.tuyucheng.taketoday.lombok;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ThankingServiceIntegrationTest {
    private final static String TRANSLATED = "TRANSLATED";

    @Test
    void thankWithTranslatedMessage() {
        Translator translator = mock(Translator.class);
        when(translator.translate("thank you")).thenReturn(TRANSLATED);
        ThankingService thankingService = new ThankingService(translator);
        assertEquals(TRANSLATED, thankingService.thank());
    }
}