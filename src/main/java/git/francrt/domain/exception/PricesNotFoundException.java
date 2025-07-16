package git.francrt.domain.exception;

public class PricesNotFoundException extends RuntimeException {
    public PricesNotFoundException() {
        super("Prices not found for the given parameters.");
    }

    public PricesNotFoundException(String message) {
        super(message);
    }
}