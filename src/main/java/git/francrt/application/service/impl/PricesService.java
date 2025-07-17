package git.francrt.application.service.impl;

import git.francrt.application.ports.context.PricesContext;
import git.francrt.application.ports.output.PricesJPAPort;
import git.francrt.application.validation.PricesValidation;
import git.francrt.domain.exception.PricesNotFoundException;
import git.francrt.domain.model.Prices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PricesService implements PricesContext {

    private final PricesJPAPort jpaPort;

    @Override
    public Prices getPrices(Long productId, Long brandId, LocalDateTime appDate) {

        PricesValidation.validateAll(productId, brandId, appDate);

        /*
         * Validate that all parameters are provided in order to reassure business integrity.
         * Infrastructure layer could be either externalized or not always grant this validation.
         */
        if (productId != null && brandId != null && appDate != null) {
            List<Prices> upFront = jpaPort.findByMultipleParameters(productId, brandId, appDate);

            return upFront.stream()
                    .max(Comparator.comparing(Prices::getPriority))
                    .orElseThrow(() -> new PricesNotFoundException(
                            "No prices found for the given parameters: productId=" + productId + ", brandId=" + brandId + ", appDate=" + appDate
                    ));
        } else {
            throw new IllegalArgumentException("All parameters must be provided: productId, brandId, appDate");
        }
    }
}

