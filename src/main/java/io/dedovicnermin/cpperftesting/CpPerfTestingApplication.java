package io.dedovicnermin.cpperftesting;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class CpPerfTestingApplication {

    public final KafkaStreams stream;

    public CpPerfTestingApplication(KafkaStreams stream) {
        this.stream = stream;
    }

    public static void main(String[] args) {
        SpringApplication.run(CpPerfTestingApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down...");
                stream.close(Duration.ofSeconds(5));
                countDownLatch.countDown();
            }));
            System.out.println("STARTING");
            try {
                stream.start();
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw e;
            }
        };
    }

}


