package by.karpovich.security.jpa.repository;

import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    List<UserEntity> findByUserStatus(UserStatus userStatus);

    @Query("SELECT u FROM UserEntity u WHERE u.applicationNotification = true")
    List<UserEntity> findAllWithApplicationNotification();
}
