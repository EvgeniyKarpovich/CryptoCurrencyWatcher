package by.karpovich.cryptoWatcher.service;

import by.karpovich.cryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.cryptoWatcher.api.dto.crypto.CoinListResponse;
import by.karpovich.cryptoWatcher.jpa.entity.CryptoEntity;
import by.karpovich.cryptoWatcher.jpa.repository.CryptoRepository;
import by.karpovich.cryptoWatcher.mapping.CryptoMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoServiceImpl {

    private final CryptoRepository cryptoRepository;
    private final CryptoMapper cryptoMapper;

    public void getCoins() {
        int start = 0;
        int limit = 100;

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        while (true) {
            String url = "https://api.coinlore.net/api/tickers/?start=" + start + "&limit=" + limit;

            String response = sendGetRequest(client, url);

            try {
                CoinListResponse coinListResponse = gson.fromJson(response, CoinListResponse.class);
                List<CoinDto> coinsDtoFromCoinLore = coinListResponse.getData();

                if (coinsDtoFromCoinLore.isEmpty()) {
                    break;
                }

                executorService.execute(() -> saveCoins(coinsDtoFromCoinLore));

                if (coinsDtoFromCoinLore.size() < limit) {
                    break;
                }

                start += limit;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private String sendGetRequest(OkHttpClient client, String url) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Transactional
    private void saveCoins(List<CoinDto> coinsDto) {
        List<CryptoEntity> cryptoEntities = new ArrayList<>();

        for (CoinDto coinDto : coinsDto) {
            CryptoEntity cryptoEntity = cryptoMapper.mapCryptoEntityFromCoinDto(coinDto);
            cryptoEntities.add(cryptoEntity);
        }

        cryptoRepository.saveAll(cryptoEntities);
    }

    @Transactional
    public void updateCoins() {
        int start = 0;
        int limit = 100;

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        List<CoinDto> result = new ArrayList<>();
        List<CryptoEntity> entitiesToUpdate = new ArrayList<>();
        List<CryptoEntity> entitiesToSave = new ArrayList<>();

        while (true) {
            String url = "https://api.coinlore.net/api/tickers/?start=" + start + "&limit=" + limit;

            String response = sendGetRequest(client, url);

            try {
                CoinListResponse coinListResponse = gson.fromJson(response, CoinListResponse.class);
                List<CoinDto> coinsDtoFromCoinLore = coinListResponse.getData();

                result.addAll(coinsDtoFromCoinLore);

                if (coinsDtoFromCoinLore.isEmpty()) {
                    break;
                }

                if (coinsDtoFromCoinLore.size() < limit) {
                    break;
                }

                start += limit;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        for (CoinDto dto : result) {
            String idFromCoinLore = dto.getId();

            Optional<CryptoEntity> cryptoEntityByIdFromCoinLore = cryptoRepository.findByIdFromCoinLore(idFromCoinLore);

            if (cryptoEntityByIdFromCoinLore.isPresent()) {

                CryptoEntity cryptoEntity = cryptoEntityByIdFromCoinLore.get();

                cryptoEntity.setPercentChange1h(dto.getPercentChange1h());
                cryptoEntity.setPercentChange7d(dto.getPercentChange7d());
                cryptoEntity.setPercentChange24h(dto.getPercentChange24h());
                cryptoEntity.setPriceInUSD(dto.getPriceUsd());
                cryptoEntity.setRank(dto.getRank());

                entitiesToUpdate.add(cryptoEntity);
            } else {
                entitiesToSave.add(cryptoMapper.mapCryptoEntityFromCoinDto(dto));
            }
        }

        if (!entitiesToSave.isEmpty()) {
            cryptoRepository.saveAll(entitiesToSave);
        }

        if (!entitiesToUpdate.isEmpty()) {
            cryptoRepository.saveAll(entitiesToUpdate);
        }
    }
}
