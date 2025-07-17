package git.francrt.application.ports.context;

import git.francrt.domain.model.Prices;

import java.time.LocalDateTime;

public interface PricesContext {

    Prices getPrices(Long productId, Long brandId, LocalDateTime appDate);
}
