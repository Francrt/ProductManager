package git.francrt.application.ports.output;

import git.francrt.domain.model.Prices;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesJPAPort {
    List<Prices> findByMultipleParameters(Long productId, Long brandId, LocalDateTime appDate);
    List<Prices> findByProductId(Long productId);
    List<Prices> findByBrandId(Long brandId);
    List<Prices> findByDate(LocalDateTime appDate);
    List<Prices> findAll();
}