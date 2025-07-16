package git.francrt.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Prices {

    @Id
    private Long priceList;
    private Long brandId;
    private Long productId;
    private LocalDateTime startDate;
    private Integer priority;
    private BigDecimal price;
    private LocalDateTime endDate;
    private String currency;
}
