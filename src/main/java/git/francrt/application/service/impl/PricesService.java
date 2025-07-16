package git.francrt.application.service.impl;

import git.francrt.application.ports.context.PricesContext;
import git.francrt.application.ports.output.PricesRepository;
import git.francrt.domain.model.Prices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PricesService implements PricesContext {

    private final PricesRepository repository;

    public PricesService(PricesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Prices> getPrices(Long productId, Long brandId, LocalDateTime appDate) {
        log.info("Filtering priceList based in parameters");


        /**
         * This whole code block could be replaced with a filtering method.
         * repository.findAll().stream().filter(price -> price.getProductId().equals(productId)...
         * Considering the sice of our dataset, this is probably the best approach, but
         * taking into account scalability I decided to use the repository methods.
         */
        if(productId != null && brandId != null && appDate != null){
            log.info("Retrieving prices for product ID: {}, brand ID: {}, on date: {}", productId, brandId, appDate);
            return repository.findByMultipleParameters(productId, brandId, appDate);
        }

        if(productId != null) {
            log.info("Retrieving prices for product ID: {}", productId);
            return repository.findByProductId(productId);
        }

        if(brandId != null) {
            log.info("Retrieving prices for brand ID: {}", brandId);
            return repository.findByBrandId(brandId);
        }

        if(appDate != null) {
            log.info("Retrieving prices for date: {}", appDate);
            return repository.findByDate(appDate);
        }

        return repository.findAll();

    }
}
