package by.karpovich.cryptoWatcher.api.clientController;

import by.karpovich.cryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.cryptoWatcher.service.CryptoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cryptos")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoServiceImpl cryptoService;

    @GetMapping("/info")
    public Integer getInfo() {
        return cryptoService.getCoinsCount();
    }

    @GetMapping("/coins")
    public List<CoinDto> get() {
        return cryptoService.getCoins();
    }

    @GetMapping("/coins2")
    public List<CoinDto> get2() {
        return cryptoService.getAllCoins();
    }

    @PostMapping
    public void save() {
        cryptoService.saveAllCoins();
    }

    @PutMapping("/updates")
    public void update() {
        cryptoService.updateAllCoins();
    }
}
