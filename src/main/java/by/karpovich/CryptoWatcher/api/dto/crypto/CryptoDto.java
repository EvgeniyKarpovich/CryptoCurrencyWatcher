package by.karpovich.CryptoWatcher.api.dto.crypto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CryptoDto {

    private String name;
    private String symbol;
    private Integer rank;
    private String priceInUSD;
    private String percentChange24h;
    private String percentChange1h;
    private String percentChange7d;

}
