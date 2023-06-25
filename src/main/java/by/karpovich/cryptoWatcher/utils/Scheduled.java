package by.karpovich.cryptoWatcher.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduled {
//
//    private final CryptoServiceImpl cryptoService;
//
//    private static final int PERIOD_MINUTES = 1;
//    private static final int INITIAL_DELAY = 0;
//    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    public void start() {
//        scheduler.scheduleAtFixedRate(this::updateCoins, INITIAL_DELAY, PERIOD_MINUTES, TimeUnit.MINUTES);
//    }
//
//    public void stop() {
//        scheduler.shutdown();
//    }
//
//    private void updateCoins() {
//        cryptoService.updateAllCoins();
//    }

//    private final CryptoServiceImpl cryptoService;
//
//    private void updatePrice() {
//        cryptoService.saveAllCoins2();
//    }
//
//    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//
//    @PostConstruct
//    public void startUpdating() {
//        executorService.scheduleAtFixedRate(this::updatePrice, 0, 1, TimeUnit.MINUTES);
//    }
//
//    @PreDestroy
//    public void stopUpdating() {
//        executorService.shutdown();
//    }
}
