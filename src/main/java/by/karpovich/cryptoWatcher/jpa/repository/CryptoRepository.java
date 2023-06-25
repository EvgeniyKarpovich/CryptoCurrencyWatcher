package by.karpovich.cryptoWatcher.jpa.repository;

import by.karpovich.cryptoWatcher.jpa.entity.CryptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoEntity, Long> {

    Optional<CryptoEntity> findByName(String name);
}
