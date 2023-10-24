package by.karpovich.security.jpa.repository;

import by.karpovich.security.jpa.entity.UserEntity;
import by.karpovich.security.jpa.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<UserEntity, Long>,
        PagingAndSortingRepository<UserEntity, Long>,
        QuerydslPredicateExecutor<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);

    List<UserEntity> findByStatus(UserStatus status);

    @Query("""
            SELECT u.*
            FROM UserEntity u
            JOIN user_roles ur ON u.id = ur.user_id
            JOIN roles r ON ur.role_id = r.id
            WHERE r.status = 'ACTIVE';
            """)
    List<UserEntity> findByRole(String role);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE UserEntity u 
            SET u.status = :status
            where u.id = :id
            """)
    void setStatus(Long id, UserStatus status);
}
