package routefinder.domain.usecase;

import java.util.List;

public interface ShortestPath<T> {

    void addRoute(T origin, T destination, int distance);

    T getNode(String s);

    List<T> findRoute(T origin, T destination);

    int getTotalDistance(List<T> route);

}
