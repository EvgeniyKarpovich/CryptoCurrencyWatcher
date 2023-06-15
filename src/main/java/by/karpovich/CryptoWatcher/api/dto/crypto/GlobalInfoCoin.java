package by.karpovich.CryptoWatcher.api.dto.crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GlobalInfoCoin {

    private Integer coinsCount;
    private Integer activeMarkets;
}
