package by.karpovich.CryptoWatcher.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cryptos")
@EntityListeners(AuditingEntityListener.class)
public class CryptoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_from_coinLore", nullable = false, unique = true)
    private String idFromCoinLore;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "price_in_usd", nullable = false)
    private String priceInUSD;

    @Column(name = "percent_change_24h", nullable = false)
    private String percentChange24h;

    @Column(name = "percent_change_1h", nullable = false)
    private String percentChange1h;

    @Column(name = "percent_change_7d", nullable = false)
    private String percentChange7d;

    @CreatedDate
    @Column(name = "date_of_creation", updatable = false)
    private Instant dateOfCreation;

    @LastModifiedDate
    @Column(name = "date_of_change")
    private Instant dateOfChange;
}
