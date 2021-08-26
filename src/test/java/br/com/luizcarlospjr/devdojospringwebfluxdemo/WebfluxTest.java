package br.com.luizcarlospjr.devdojospringwebfluxdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public abstract class WebfluxTest {

    @BeforeAll
    public static void blockHoundSetUp() {
        BlockHound.install();
    }

    @Test
    @DisplayName("Given that Block Hound is installed; When call for a thread-locker process; Should throw a blocking operation error")
    void givenThatBlockHoundIsInstalled_whenCallForAThreadLockerProcess_shouldThrowABlockingOperationError() {
        FutureTask<String> task = new FutureTask<>(() -> {
            Thread.sleep(0);
            return "";
        });

        Schedulers.parallel().schedule(task);

        try {
            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("Should fail");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

}
