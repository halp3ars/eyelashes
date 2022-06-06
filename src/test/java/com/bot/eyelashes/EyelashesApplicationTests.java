package com.bot.eyelashes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

//@SpringBootTest
class EyelashesApplicationTests {

    @Test
    void contextLoads() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<Object> callable = new Callable<>() {

            @Override
            public Object call() throws Exception {
                try {
                    System.out.println("Тут");
                    sleep(10000);
                } catch (Exception ex) {

                }
                return null;
            }
        };

        executorService.submit(callable);
        executorService.submit(callable);
    }

}
