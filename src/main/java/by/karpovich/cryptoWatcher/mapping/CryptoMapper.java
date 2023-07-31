package by.karpovich.cryptoWatcher.mapping;

import by.karpovich.cryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.cryptoWatcher.jpa.entity.CryptoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CryptoMapper {

    public CryptoEntity mapCryptoEntityFromCoinDto(CoinDto coinDto) {

        return Optional.ofNullable(coinDto)
                .map(dto -> CryptoEntity.builder()
                        .idFromCoinLore(dto.getId())
                        .name(dto.getName())
                        .symbol(dto.getSymbol())
                        .rank(dto.getRank())
                        .priceInUSD(dto.getPriceUsd())
                        .percentChange1h(dto.getPercentChange1h())
                        .percentChange24h(dto.getPercentChange24h())
                        .percentChange7d(dto.getPercentChange7d())
                        .build())
                .orElse(null);
    }

    public List<CryptoEntity> mapListEntityFromListCoinDto(List<CoinDto> coinsDto) {
        if (coinsDto == null) {
            return null;
        }

        List<CryptoEntity> cryptoEntities = new ArrayList<>();

        for (CoinDto coinDto : coinsDto) {
            cryptoEntities.add(mapCryptoEntityFromCoinDto(coinDto));
        }

        return cryptoEntities;
    }
}
