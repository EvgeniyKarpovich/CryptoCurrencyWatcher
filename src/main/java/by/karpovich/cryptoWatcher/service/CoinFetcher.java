package by.karpovich.cryptoWatcher.service;

import by.karpovich.cryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.cryptoWatcher.api.dto.crypto.CoinListResponse;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;
import java.util.concurrent.Callable;

public class CoinFetcher implements Callable<List<CoinDto>> {

    private int start;

    public CoinFetcher(int start) {
        this.start = start;
    }

    @Override
    public List<CoinDto> call() throws Exception {

        // Создайте новый клиент OkHttp.
        OkHttpClient client = new OkHttpClient();

        // Постройте URL-адрес для API-запроса.
        String url = String.format("https://api.coinlore.net/api/tickers/?start=%d&limit=100", start);

        // Создайте новый объект запроса с помощью URL-адреса.
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Отправьте запрос и получите ответ.
        Response response = client.newCall(request).execute();

        // Извлеките тело ответа как строку JSON.
        String responseBody = response.body().string();

        // Разберите строку JSON с помощью Gson.
        Gson gson = new Gson();
        CoinListResponse coinListResponse = gson.fromJson(responseBody, CoinListResponse.class);

        // Верните список монет.
        return coinListResponse.getData();
    }

}
