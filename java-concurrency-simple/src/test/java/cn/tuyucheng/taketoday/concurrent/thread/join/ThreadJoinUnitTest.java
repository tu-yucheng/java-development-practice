package cn.tuyucheng.taketoday.concurrent.thread.join;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Demonstrates Thread.join() behavior.
 */
class ThreadJoinUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadJoinUnitTest.class);

    static class SampleThread extends Thread {
        public int processingCount;

        SampleThread(int processingCount) {
            this.processingCount = processingCount;
            LOGGER.debug("Thread " + this.getName() + " created");
        }

        @Override
        public void run() {
            LOGGER.debug("Thread " + this.getName() + " started");
            while (processingCount > 0) {
                try {
                    Thread.sleep(1000); // Simulate some work being done by thread
                } catch (InterruptedException e) {
                    LOGGER.debug("Thread " + this.getName() + " interrupted.");
                }
                processingCount--;
                LOGGER.debug("Inside Thread " + this.getName() + ", processingCount = " + processingCount);
            }
            LOGGER.debug("Thread " + this.getName() + " exiting");
        }
    }

    @Test
    void givenNewThread_whenJoinCalled_returnsImmediately() throws InterruptedException {
        Thread t1 = new SampleThread(0);
        LOGGER.debug("Invoking join.");
        t1.join();
        LOGGER.debug("Returned from join");
        LOGGER.debug("Thread state is" + t1.getState());
        assertFalse(t1.isAlive());
    }

    @Test
    void givenStartedThread_whenJoinCalled_waitsTillCompletion() throws InterruptedException {
        Thread t2 = new SampleThread(1);
        t2.start();
        LOGGER.debug("Invoking join.");
        t2.join();
        LOGGER.debug("Returned from join");
        assertFalse(t2.isAlive());
    }

    @Test
    void givenStartedThread_whenTimedJoinCalled_waitsUntilTimedOut() throws InterruptedException {
        Thread t3 = new SampleThread(10);
        t3.start();
        t3.join(1000);
        assertTrue(t3.isAlive());
    }
    
    @Test
    @Disabled("test that doesn't stop")
    void givenThreadTerminated_checkForEffect_notGuaranteed() {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        // not guaranteed to stop even if t4 finishes.
        do {

        } while (t4.processingCount > 0);
        assertTrue(true);
    }

    @Test
    void givenJoinWithTerminatedThread_checkForEffect_guaranteed() throws InterruptedException {
        SampleThread t4 = new SampleThread(10);
        t4.start();
        do {
            t4.join(100);
        } while (t4.processingCount > 0);
    }
}