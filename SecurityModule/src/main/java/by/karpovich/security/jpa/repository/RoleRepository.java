package by.karpovich.security.jpa.repository;

import by.karpovich.security.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, PagingAndSortingRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(String name);
}
