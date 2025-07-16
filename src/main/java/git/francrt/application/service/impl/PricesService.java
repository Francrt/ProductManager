package git.francrt.application.service.impl;

import git.francrt.application.ports.context.PricesContext;
import git.francrt.application.ports.output.PricesJPAPort;
import git.francrt.application.validation.PricesValidation;
import git.francrt.domain.exception.PricesNotFoundException;
import git.francrt.domain.model.Prices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PricesService implements PricesContext {

    private final PricesJPAPort jpaPort;

    @Override
    public List<Prices> getPrices(Long productId, Long brandId, LocalDateTime appDate) {

        PricesValidation.validateAll(productId, brandId, appDate);

        /**
         * This whole code block could be replaced with a filtering method.
         * repository.findAll().stream().filter(price -> price.getProductId().equals(productId)...
         * Considering the sice of our dataset, this is probably the best approach, but
         * taking into account scalability I decided to use the repository methods.
         */

        if (productId != null && brandId != null && appDate != null) {
            List<Prices> upFront = jpaPort.findByMultipleParameters(productId, brandId, appDate);

            if (upFront.isEmpty()){
                throw new PricesNotFoundException("No prices found for the given parameters: productId=" + productId + ", brandId=" + brandId + ", appDate=" + appDate);
            }

            return List.of(upFront.stream()
                            .max((p1, p2) -> p1.getPriority().compareTo(p2.getPriority()))
                            .get()
            );
        } else {
            throw new IllegalArgumentException("All parameters must be provided: productId, brandId, appDate");
        }
    }
}

