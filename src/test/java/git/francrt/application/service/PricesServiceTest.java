package git.francrt.application.service;

import git.francrt.application.ports.context.PricesContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class PricesServiceTest {

    @Autowired
    private PricesContext context;

    @Test
    void testFirstRequest() {
        var price = context.getPrices(35455L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0));
        assertThat(price).isNotNull().hasSize(1);
    }

    @Test
    void testSecondRequest() {
        var price = context.getPrices(35455L, 1L, LocalDateTime.of(2020, 6, 14, 16, 0));
        assertThat(price).isNotNull().hasSize(2);
    }

    @Test
    void testThirdRequest() {
        var price = context.getPrices(35455L, 1L, LocalDateTime.of(2020, 6, 14, 21, 0));
        assertThat(price).isNotNull().hasSize(1);
    }

    @Test
    void testFourthRequest() {
        var price = context.getPrices(35455L, 1L, LocalDateTime.of(2020, 6, 15, 10, 0));
        assertThat(price).isNotNull().hasSize(2);
    }

    @Test
    void testFifthRequest() {
        var price = context.getPrices(35455L, 1L, LocalDateTime.of(2020, 6, 16, 21, 0));
        assertThat(price).isNotNull().hasSize(2);
    }
}
