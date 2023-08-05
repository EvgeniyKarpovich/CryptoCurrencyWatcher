package by.karpovich.cryptoWatcher.utils;

import by.karpovich.cryptoWatcher.service.CryptoServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduled {

    private final CryptoServiceImpl cryptoService;
    private static final int FIXED_RATE_IN_MILLISECONDS = 300000;

    @org.springframework.scheduling.annotation.Scheduled(fixedRate = FIXED_RATE_IN_MILLISECONDS)
    public void updateCoins() {
        try {
            cryptoService.updateCoins();
            log.info("Монеты успешно обновлены.");
        } catch (Exception e) {
            log.error("Ошибка при обновлении монет.", e);
        }
    }
}