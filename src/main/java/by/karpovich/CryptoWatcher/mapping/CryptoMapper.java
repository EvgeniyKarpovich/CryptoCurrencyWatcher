package by.karpovich.CryptoWatcher.mapping;

import by.karpovich.CryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.CryptoWatcher.jpa.entity.CryptoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CryptoMapper {

    public CryptoEntity mapCryptoEntityFromCoinDto(CoinDto coinDto) {
        if (coinDto == null) {
            return null;
        }

        return CryptoEntity.builder()
                .idFromCoinLore(coinDto.getId())
                .name(coinDto.getName())
                .symbol(coinDto.getSymbol())
                .rank(coinDto.getRank())
                .priceInUSD(coinDto.getPriceUsd())
                .percentChange1h(coinDto.getPercentChange1h())
                .percentChange24h(coinDto.getPercentChange24h())
                .percentChange7d(coinDto.getPercentChange7d())
                .build();
    }

    public List<CryptoEntity> mapListEntityFromListCoinDto(List<CoinDto> coinsDto) {
        if (coinsDto == null) {
            return null;
        }

        List<CryptoEntity> cryptoEntities = new ArrayList<>();

        for (CoinDto coinDto  :  coinsDto) {
            cryptoEntities.add(mapCryptoEntityFromCoinDto(coinDto));
        }

        return cryptoEntities;
    }
}
