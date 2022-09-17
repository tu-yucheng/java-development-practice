package cn.tuyucheng.taketoday.beanpostprocessor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PostProcessorConfiguration.class})
class StockTradeIntegrationTest {

    @Autowired
    private StockTradePublisher stockTradePublisher;

    @Test
    void givenValidConfig_whenTradePublished_thenTradeReceived() {
        Date tradeDate = new Date();
        StockTrade stockTrade = new StockTrade("AMZN", 100, 2483.52d, tradeDate);
        AtomicBoolean assertionsPassed = new AtomicBoolean(false);
        StockTradeListener listener = trade -> assertionsPassed.set(this.verifyExact(stockTrade, trade));
        this.stockTradePublisher.addStockTradeListener(listener);
        try {
            GlobalEventBus.post(stockTrade);
            await().atMost(Duration.ofSeconds(2L)).untilAsserted(() -> assertThat(assertionsPassed.get()).isTrue());
        } finally {
            this.stockTradePublisher.removeStockTradeListener(listener);
        }
    }

    private boolean verifyExact(StockTrade stockTrade, StockTrade trade) {
        return Objects.equals(stockTrade.symbol(), trade.symbol())
                && Objects.equals(stockTrade.tradeDate(), trade.tradeDate())
                && stockTrade.quantity() == trade.quantity()
                && stockTrade.price() == trade.price();
    }
}