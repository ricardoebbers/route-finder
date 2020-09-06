package routefinder.domain.usecase.astar;

import routefinder.domain.entity.Graph;
import routefinder.domain.entity.Node;
import routefinder.domain.entity.Scorer;
import routefinder.domain.exception.RouteNotFoundException;
import routefinder.domain.usecase.ShortestPath;

import java.util.*;

public class RouteFinder<T extends Node> implements ShortestPath<T> {

    private final Graph<T> graph;
    private final Scorer<T> scorer;
    private Queue<RouteNode<T>> openSet;
    private Map<T, RouteNode<T>> allNodes;

    public RouteFinder(Graph<T> graph, Scorer<T> scorer) {
        this.graph = graph;
        this.scorer = scorer;
    }

    @Override
    public void addRoute(T origin, T destination, int distance) {
        graph.addRoute(origin, destination, distance);
    }

    @Override
    public T getNode(String s) {
        return graph.getNode(s);
    }

    public List<T> findRoute(T origin, T destination) {
        if (origin == null || destination == null) {
            throw new RouteNotFoundException();
        }
        initialize(origin, destination);
        return findRoute(destination);
    }

    @Override
    public int getTotalDistance(List<T> route) {
        return scorer.getTotalDistance(route);
    }

    private void initialize(T origin, T destination) {
        openSet = new PriorityQueue<>();
        allNodes = new HashMap<>();
        RouteNode<T> start = RouteNode.start(origin, scorer.computeCost(origin, destination));
        openSet.add(start);
        allNodes.put(origin, start);
    }

    private List<T> findRoute(T destination) {
        while (!openSet.isEmpty()) {
            RouteNode<T> node = openSet.poll();
            if (node.getCurrent().equals(destination)) {
                return buildRoute(node);
            }
            graph.getConnections(node.getCurrent()).forEach(connection ->
                    recomputeScores(destination, node, connection)
            );
        }
        throw new RouteNotFoundException();
    }

    private void recomputeScores(T destination, RouteNode<T> next, T connection) {
        RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
        allNodes.put(connection, nextNode);
        int newScore = next.getRouteScore() + scorer.computeCost(next.getCurrent(), connection);
        if (newScore < nextNode.getRouteScore()) {
            addNodeToOpenSet(destination, next, connection, nextNode, newScore);
        }
    }

    private void addNodeToOpenSet(T destination, RouteNode<T> next, T connection, RouteNode<T> nextNode, int newScore) {
        nextNode.setPrevious(next.getCurrent());
        nextNode.setRouteScore(newScore);
        nextNode.setEstimatedScore(newScore + scorer.computeCost(connection, destination));
        openSet.add(nextNode);
    }

    private List<T> buildRoute(RouteNode<T> next) {
        List<T> route = new ArrayList<>();
        RouteNode<T> current = next;
        do {
            route.add(0, current.getCurrent());
            current = allNodes.get(current.getPrevious());
        } while (current != null);
        System.out.printf("Route %s found!%n", route);
        return route;
    }

}
