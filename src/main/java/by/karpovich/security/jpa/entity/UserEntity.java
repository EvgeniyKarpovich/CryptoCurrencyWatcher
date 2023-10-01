package by.karpovich.security.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<NotificationEntity> notifications = new ArrayList<>();

    @Column(name = "image")
    private String image;

    @Column(name = "percent")
    private String percent;

//    @Column(name = "email_notification", columnDefinition = "boolean default false")
//    private boolean emailNotification;

    @Column(name = "application_notification", columnDefinition = "boolean default false")
    private boolean applicationNotification;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();
}
