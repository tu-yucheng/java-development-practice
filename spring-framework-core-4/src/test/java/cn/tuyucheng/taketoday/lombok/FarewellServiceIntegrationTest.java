package cn.tuyucheng.taketoday.lombok;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FarewellServiceIntegrationTest {
    private final static String TRANSLATED = "TRANSLATED";

    @Test
    void sayByeWithTranslatedMessage() {
        Translator translator = mock(Translator.class);
        when(translator.translate("bye")).thenReturn(TRANSLATED);
        FarewellService farewellService = new FarewellService(translator);
        assertEquals(TRANSLATED, farewellService.farewell());
    }
}