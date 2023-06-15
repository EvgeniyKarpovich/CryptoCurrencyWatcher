package by.karpovich.CryptoWatcher.api.dto.crypto;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GlobalCryptoData {

    @SerializedName("coins_count")
    private Integer coinsCount;
    @SerializedName("active_markets")
    private Integer activeMarkets;
    @SerializedName("total_mcap")
    private Double totalMcap;
    @SerializedName("total_volume")
    private Double totalVolume;
    @SerializedName("btc_d")
    private String btcD;
    @SerializedName("eth_d")
    private String ethD;
    @SerializedName("mcap_change")
    private String mcapChange;
    @SerializedName("volume_change")
    private String volumeChange;
    @SerializedName("avg_change_percent")
    private String avgChangePercent;
    @SerializedName("volume_ath")
    private Long volumeAth;
    @SerializedName("mcap_ath")
    private Double mcapAth;

}
