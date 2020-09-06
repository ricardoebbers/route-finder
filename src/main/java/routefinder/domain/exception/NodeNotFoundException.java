package routefinder.domain.exception;

public class NodeNotFoundException extends RuntimeException {

    public NodeNotFoundException() {
        super("Node not found!");
    }

}
