package routefinder.domain.repository;

import routefinder.domain.entity.Node;

import java.util.List;
import java.util.Set;

public interface DistanceRepository<T extends Node> {

    void saveDistance(T origin, T destination, int distance);

    int getDistance(T origin, T destination);

    T getNodeById(String id);

    Set<T> getConnections(String id);

    int getTotalDistance(List<T> route);

}
