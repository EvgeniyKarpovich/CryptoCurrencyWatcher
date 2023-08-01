package by.karpovich.cryptoWatcher.jpa.repository;

import by.karpovich.cryptoWatcher.jpa.entity.CryptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoEntity, Long> {

    Optional<CryptoEntity> findByName(String name);

//    @Query("SELECT c FROM CryptoEntity c WHERE c.idFromCoinLore = (SELECT MAX(c2.idFromCoinLore) FROM CryptoEntity c2)")
//    CryptoEntity findCryptoEntityWithMaxIdFromCoinLore();

    @Query("SELECT MAX(idFromCoinLore) FROM CryptoEntity")
    String findMaxIdFromCoinLoreFromCryptoEntity();

    Optional<CryptoEntity> findByIdFromCoinLore(String id);
}
