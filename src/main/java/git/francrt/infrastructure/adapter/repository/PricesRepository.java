package git.francrt.infrastructure.adapter.repository;

import git.francrt.application.ports.output.PricesJPAPort;
import git.francrt.domain.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesRepository extends JpaRepository<Prices, Long>, PricesJPAPort {

    List<Prices> findByProductId(Long productId);
    List<Prices> findByBrandId(Long brandId);

    @Query("SELECT p FROM Prices p WHERE :appDate BETWEEN p.startDate AND p.endDate")
    List<Prices> findByDate(@Param("appDate") LocalDateTime date);

    @Query("SELECT p FROM Prices p WHERE p.productId = :productId AND p.brandId = :brandId AND :appDate BETWEEN p.startDate AND p.endDate")
    List<Prices> findByMultipleParameters(
            @Param("productId") Long productId,
            @Param("brandId") Long brandId,
            @Param("appDate") LocalDateTime appDate
            );

}
