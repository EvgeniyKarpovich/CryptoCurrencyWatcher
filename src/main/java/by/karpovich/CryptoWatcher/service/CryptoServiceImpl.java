package by.karpovich.CryptoWatcher.service;

import by.karpovich.CryptoWatcher.api.dto.crypto.CoinDto;
import by.karpovich.CryptoWatcher.api.dto.crypto.GlobalCryptoData;
import by.karpovich.CryptoWatcher.jpa.entity.CryptoEntity;
import by.karpovich.CryptoWatcher.jpa.repository.CryptoRepository;
import by.karpovich.CryptoWatcher.mapping.CryptoMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl {

    private final CryptoRepository cryptoRepository;
    private final CryptoMapper cryptoMapper;


    //Получаем количество всех койнов с coinlore
    public Integer getCoinsCount() {
        Response response = null;
        String responseJson = "";
        GlobalCryptoData dto = null;

        OkHttpClient client = new OkHttpClient();

        String url = "https://api.coinlore.net/api/global/";

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseJson = response.body().string();

                Gson gson = new Gson();

                GlobalCryptoData[] coinDto = gson.fromJson(responseJson, GlobalCryptoData[].class);
                dto = coinDto[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dto.getCoinsCount();
    }

    //Получаю все монеты с coinlore
    public List<CoinDto> getAllCoins() {
        Integer coinsCount = getCoinsCount();
        try {
            // Создаю список будущих результатов.
            List<Future<List<CoinDto>>> futures = new ArrayList<>();

            // Создайте пул потоков с фиксированным количеством потоков.
            int numThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(numThreads);

            // Выполняю запросы, используя пул потоков.
            for (int start = 0; start < coinsCount; start += 100) {
                Callable<List<CoinDto>> task = new CoinFetcher(start);
                Future<List<CoinDto>> future = executor.submit(task);
                futures.add(future);
            }

            // Ожидание завершения всех запросов и получение результатов.
            List<CoinDto> allCoins = new ArrayList<>();
            for (Future<List<CoinDto>> future : futures) {
                allCoins.addAll(future.get());
            }

            // Остановка пул потоков.
            executor.shutdown();

            return allCoins;
        } catch (Exception e) {

            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
            return Collections.emptyList(); // вернуть пустой список
        }
    }

    @Transactional
    public void saveAllCoinsFrom() {
        List<CoinDto> allCoins = getAllCoins();
        for (CoinDto coinDto : allCoins) {
            CryptoEntity cryptoEntity = cryptoMapper.mapCryptoEntityFromCoinDto(coinDto);
            cryptoRepository.save(cryptoEntity);
        }
    }
}
