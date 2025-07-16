package git.francrt.application.service;

import git.francrt.application.ports.context.PricesContext;
import git.francrt.domain.exception.PricesNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = "/test-dataset.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PricesServiceTests {

    @Autowired
    private PricesContext context;

    static Stream<Arguments> provideValidRequests() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2020, 6, 14, 10, 0)),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 16, 0)),
                Arguments.of(LocalDateTime.of(2020, 6, 14, 21, 0)),
                Arguments.of(LocalDateTime.of(2020, 6, 15, 10, 0)),
                Arguments.of(LocalDateTime.of(2020, 6, 16, 21, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidRequests")
    void testValidRequests(LocalDateTime date) {
        var price = context.getPrices(35455L, 1L, date);
        assertThat(price).isNotNull().hasSize(1);
    }

    @Test
    void testInvalidProductId() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> context.getPrices(-1L, 1L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("productId");
    }

    @Test
    void testInvalidBrandId() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> context.getPrices(1L, -2L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("brandId");
    }

    @Test
    void testNullDate() {
        assertThatThrownBy(() -> context.getPrices(35455L, 1L, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("appDate");
    }

    @Test
    void testMultipleResultsReturnsHighestPriority() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 18, 30);
        var price = context.getPrices(35455L, 1L, date);
        assertThat(price).isNotNull().hasSize(1);
        assertThat(price.getFirst().getPriority()).isEqualTo(1);
    }

    @Test
    void testNonExistentIds() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> context.getPrices(99999L, 88888L, now))
                .isInstanceOf(PricesNotFoundException.class);
    }

    @Test
    void testDateOutOfRange() {
        LocalDateTime date = LocalDateTime.of(1990, 1, 1, 0, 0);
        assertThatThrownBy(() -> context.getPrices(35455L, 1L, date))
                .isInstanceOf(PricesNotFoundException.class);
    }

    @Test
    void testNullProductId() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> context.getPrices(null, 1L, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("productId");
    }

    @Test
    void testNullBrandId() {
        LocalDateTime now = LocalDateTime.now();
        assertThatThrownBy(() -> context.getPrices(35455L, null, now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("brandId");
    }
}
