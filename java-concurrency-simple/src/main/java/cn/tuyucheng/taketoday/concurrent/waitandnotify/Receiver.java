package cn.tuyucheng.taketoday.concurrent.waitandnotify;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Receiver implements Runnable {
    private final Data data;

    public Receiver(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        for (String receivedMessage = data.receive(); !"End".equals(receivedMessage); receivedMessage = data.receive()) {
            log.info(receivedMessage);

            // Thread.sleep() to mimic heavy server-side processing
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("context ", e);
            }
        }
    }
}