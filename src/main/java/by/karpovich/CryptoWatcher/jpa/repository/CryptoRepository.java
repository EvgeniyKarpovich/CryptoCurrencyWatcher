package by.karpovich.CryptoWatcher.jpa.repository;

import by.karpovich.CryptoWatcher.jpa.entity.CryptoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoEntity, Long> {
}
