package routefinder.domain.exception;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException() {
        super("Route not found!");
    }

}
