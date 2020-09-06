package routefinder.domain.repository.impl;

import routefinder.domain.entity.Node;
import routefinder.domain.entity.Path;
import routefinder.domain.exception.InvalidDistanceException;
import routefinder.domain.exception.NodeNotFoundException;
import routefinder.domain.repository.DistanceRepository;

import java.util.*;

public class DistanceRepositoryImpl<T extends Node> implements DistanceRepository<T> {

    private final Map<T, Set<T>> connections = new HashMap<>();
    private final Map<Path<T>, Integer> distances = new HashMap<>();

    @Override
    public void saveDistance(T origin, T destination, int distance) {
        if (distance <= 0) throw new InvalidDistanceException();
        if (connections.containsKey(origin)) {
            connections.get(origin).add(destination);
        } else {
            Set<T> set = new HashSet<>();
            set.add(destination);
            connections.put(origin, set);
        }
        connections.putIfAbsent(destination, new HashSet<>());
        distances.put(Path.of(origin, destination), distance);
    }

    @Override
    public int getDistance(T origin, T destination) {
        return distances.getOrDefault(Path.of(origin, destination), Integer.MAX_VALUE);
    }

    @Override
    public T getNodeById(String id) {
        return connections.keySet().stream()
                .filter(node -> node.getId().equals(id))
                .findFirst().orElseThrow(NodeNotFoundException::new);
    }

    @Override
    public Set<T> getConnections(String id) {
        return connections.get(getNodeById(id));
    }

    @Override
    public int getTotalDistance(List<T> route) {
        int sum = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            sum += getDistance(route.get(i), route.get(i + 1));
        }
        return sum;
    }

}
