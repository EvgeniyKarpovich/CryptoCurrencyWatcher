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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoServiceImpl {

    private final CryptoRepository cryptoRepository;
    private final CryptoMapper cryptoMapper;

//    public void getCoins() {
//        int start = 0;
//        int limit = 100;
//
//        OkHttpClient client = new OkHttpClient();
//        Gson gson = new Gson();
//
//        while (true) {
//            String url = "https://api.coinlore.net/api/tickers/?start=" + start + "&limit=" + limit;
//
//         String   response = sendGetRequest(client, url);
//
//            try {
//                CoinListResponse coinListResponse = gson.fromJson(response, CoinListResponse.class);
//                List<CoinDto> coinsDtoFromCoinLore = coinListResponse.getData();
//
//                if (coinsDtoFromCoinLore.isEmpty()) {
//                    break;
//                }
//
//                saveCoins(coinsDtoFromCoinLore);
//
//                if (coinsDtoFromCoinLore.size() < limit) {
//                    break;
//                }
//
//                start += limit;
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void getCoins() {
        int start = 0;
        int limit = 100;

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();

        ExecutorService executorService = Executors.newFixedThreadPool(10); // Используйте оптимальное количество потоков

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
            executorService.awaitTermination(1, TimeUnit.HOURS); // Подождите, пока все потоки завершатся
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


    //Получаем количество всех койнов с coinlore
//    public Integer getCoinsCount() {
//        Response response = null;
//        String responseJson = "";
//        GlobalCryptoData dto = null;
//
//        OkHttpClient client = new OkHttpClient();
//
//        String url = "https://api.coinlore.net/api/global/";
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try {
//            response = client.newCall(request).execute();
//
//            if (response.isSuccessful()) {
//                responseJson = response.body().string();
//
//                Gson gson = new Gson();
//
//                GlobalCryptoData[] coinDto = gson.fromJson(responseJson, GlobalCryptoData[].class);
//                dto = coinDto[0];
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return dto.getCoinsCount();
//    }
//
//    //Получаю все монеты с coinlore
//    public List<CoinDto> getAllCoins() {
//        Integer coinsCount = getCoinsCount();
//        try {
//            // Создаю список будущих результатов.
//            List<Future<List<CoinDto>>> futures = new ArrayList<>();
//
//            // Создайте пул потоков с фиксированным количеством потоков.
//            int numThreads = Runtime.getRuntime().availableProcessors();
//            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//
//            // Выполняю запросы, используя пул потоков.
//            for (int start = 0; start < coinsCount; start += 100) {
//                Callable<List<CoinDto>> task = new CoinFetcher(start);
//                Future<List<CoinDto>> future = executor.submit(task);
//                futures.add(future);
//            }
//
//            // Ожидание завершения всех запросов и получение результатов.
//            List<CoinDto> allCoins = new ArrayList<>();
//            for (Future<List<CoinDto>> future : futures) {
//                allCoins.addAll(future.get());
//            }
//
//            // Остановка пул потоков.
//            executor.shutdown();
//
//            return allCoins;
//        } catch (Exception e) {
//
//            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
//            return Collections.emptyList(); // вернуть пустой список
//        }
//    }

//    public List<CoinDto> getCoins() {
//        Integer coinsCount = getCoinsCount();
//
//        try {
//            int numThreads = Runtime.getRuntime().availableProcessors();
//            ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//
//            List<CompletableFuture<List<CoinDto>>> futures = new ArrayList<>();
//            for (int start = 0; start < coinsCount; start += 100) {
//                int finalStart = start;
//                CompletableFuture<List<CoinDto>> future =
//                        CompletableFuture.supplyAsync(() -> {
//                            List<CoinDto> coins = new ArrayList<>();
//                            try {
//                                OkHttpClient client = new OkHttpClient();
//                                String url = String.format("https://api.coinlore.net/api/tickers/?start=%d&limit=100", finalStart);
//                                Request request = new Request.Builder().url(url).build();
//                                Response response = client.newCall(request).execute();
//                                String responseBody = response.body().string();
//                                Gson gson = new Gson();
//                                CoinListResponse coinListResponse = gson.fromJson(responseBody, CoinListResponse.class);
//                                coins.addAll(coinListResponse.getData());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            return coins;
//                        }, executor);
//                futures.add(future);
//            }
//
//            List<CoinDto> allCoins = futures.stream()
//                    .flatMap(future -> future.join().stream())
//                    .collect(Collectors.toList());
//
//            executor.shutdown();
//            return allCoins;
//
//        } catch (Exception e) {
//            System.err.println("Error executing request: " + e.getMessage());
//            return Collections.emptyList();
//        }
//    }
//
//    @Transactional
//    public void saveAllCoins() {
//        List<CoinDto> allCoinsFromCoinLore = getCoins();
//
//        List<CryptoEntity> collect = allCoinsFromCoinLore.stream()
//                .map(cryptoMapper::mapCryptoEntityFromCoinDto)
//                .toList();
//
//        cryptoRepository.saveAll(collect);
//    }
}
