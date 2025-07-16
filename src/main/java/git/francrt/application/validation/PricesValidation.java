package git.francrt.application.validation;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class PricesValidation {

    public static void validateProductId(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId cannot be null");
        }
        if (productId < 0) {
            throw new IllegalArgumentException("productId must have a positive value");
        }
    }

    public static void validateBrandId(Long brandId) {
        if (brandId == null) {
            throw new IllegalArgumentException("brandId cannot be null");
        }
        if (brandId < 0) {
            throw new IllegalArgumentException("brandId must have a positive value");
        }
    }

    public static void validateAppDate(LocalDateTime appDate) {
        if (appDate == null) {
            throw new IllegalArgumentException("appDate cannot be null");
        }
        if (appDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("appDate cannot be after the current date and time");
        }
    }

    public static void validateAll(Long productId, Long brandId, LocalDateTime appDate) {
        validateProductId(productId);
        validateBrandId(brandId);
        validateAppDate(appDate);
    }
}
