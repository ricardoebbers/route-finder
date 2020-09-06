package routefinder.domain.exception;

public class InvalidDistanceException extends RuntimeException {

    public InvalidDistanceException() {
        super("Distance must be positive!");
    }

}
