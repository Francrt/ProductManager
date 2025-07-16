package git.francrt.application.ports.output;

import git.francrt.domain.model.Prices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<Prices, Long> {

}
