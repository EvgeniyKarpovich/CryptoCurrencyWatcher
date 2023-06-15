package by.karpovich.CryptoWatcher.api.clientController;

import by.karpovich.CryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.CryptoWatcher.service.CryptoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cryptos")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoServiceImpl cryptoService;

//    @GetMapping("/info")
//    public GlobalCryptoData getInfo() {
//        return cryptoService.getInfo();
//    }

    @GetMapping("/info")
    public Integer getInfo() {
        return cryptoService.getCoinsCount();
    }

    @GetMapping("/coins")
    public List<CoinDto> get() {
        return cryptoService.getAllCoins();
    }

    @PostMapping("/save")
    public void save() {
        cryptoService.saveAllCoinsFrom();
    }
}
