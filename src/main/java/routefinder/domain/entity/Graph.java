package routefinder.domain.entity;

import java.util.Set;

public interface Graph<T extends Node> {

    T getNode(String id);

    Set<T> getConnections(T node);

    void addRoute(T origin, T destination, int distance);

}
