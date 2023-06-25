package by.karpovich.cryptoWatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CryptoWatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoWatcherApplication.class, args);
    }
}
