package by.karpovich.cryptoWatcher.api.dto.crypto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CoinListResponse {

    @SerializedName("data")
    @Expose
    private List<CoinDto> data;
}
