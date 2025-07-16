package git.francrt.infrastructure.configuration;

import git.francrt.infrastructure.adapter.repository.PricesRepository;
import git.francrt.domain.model.Prices;
import git.francrt.utils.Constants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DBInitializer implements CommandLineRunner {

    private final PricesRepository repository;

    public DBInitializer(PricesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(saveFirstPrices());
        repository.save(saveSecondPrices());
        repository.save(saveThirdPrices());
        repository.save(saveFourthPrices());

    }


    private Prices saveFirstPrices() {
        Prices price = Prices.builder()
                        .brandId(1L)
                        .productId(35455L)
                        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                        .priority(0)
                        .price(new BigDecimal("35.50"))
                        .currency(Constants.CURRENCY)
                        .priceList(1L)
                        .build();
        return repository.save(price);
    }

    private Prices saveSecondPrices() {
        Prices price = Prices.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency(Constants.CURRENCY)
                .priceList(2L)
                .build();
        return repository.save(price);
    }

    private Prices saveThirdPrices() {
        Prices price = Prices.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0, 0))
                .priority(1)
                .price(new BigDecimal("30.50"))
                .currency(Constants.CURRENCY)
                .priceList(3L)
                .build();
        return repository.save(price);
    }

    private Prices saveFourthPrices() {
        Prices price = Prices.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 15, 16, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .priority(0)
                .price(new BigDecimal("38.95"))
                .currency(Constants.CURRENCY)
                .priceList(4L)
                .build();
        return repository.save(price);
    }
}
