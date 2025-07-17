package git.francrt.infrastructure.adapter.rest;

import git.francrt.application.ports.context.PricesContext;
import git.francrt.application.service.mapper.PricesConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.PricesApi;
import org.openapitools.model.PricesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/prices")
@Slf4j
public class PricesController implements PricesApi {

    private final PricesContext context;
    private final PricesConverter converter;


    @GetMapping
    @Override
    public ResponseEntity<PricesDTO> getPrices(
            @RequestParam(value = "appDate", required = false) LocalDateTime appDate,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "brandId", required = false) Long brandId) {
        return ResponseEntity.ok(converter.toDTO(context.getPrices(productId, brandId, appDate)));
    }
}
