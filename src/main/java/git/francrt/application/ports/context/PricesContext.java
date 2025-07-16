package git.francrt.application.ports.context;

import git.francrt.domain.model.Prices;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesContext {

    List<Prices> getPrices(Long productId, Long brandId, LocalDateTime appDate);
}
