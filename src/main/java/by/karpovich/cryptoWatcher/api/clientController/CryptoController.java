package by.karpovich.cryptoWatcher.api.clientController;

import by.karpovich.cryptoWatcher.service.CryptoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cryptos")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoServiceImpl cryptoService;

    @PostMapping
    public void save() {
        cryptoService.getCoins();
    }

    @PutMapping
    public void update() {
        cryptoService.updateCoins();
    }
}
