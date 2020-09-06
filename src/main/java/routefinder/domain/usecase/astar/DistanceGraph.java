package routefinder.domain.usecase.astar;

import routefinder.domain.entity.Graph;
import routefinder.domain.entity.Node;
import routefinder.domain.repository.DistanceRepository;

import java.util.Set;

public class DistanceGraph<T extends Node> implements Graph<T> {

    private final DistanceRepository<T> repository;

    public DistanceGraph(DistanceRepository<T> repository) {
        this.repository = repository;
    }

    public T getNode(String id) {
        return repository.getNodeById(id);
    }

    public Set<T> getConnections(T node) {
        return repository.getConnections(node.getId());
    }

    public void addRoute(T origin, T destination, int distance) {
        repository.saveDistance(origin, destination, distance);
    }

}
