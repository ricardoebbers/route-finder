package routefinder.domain.usecase.astar;

import routefinder.domain.entity.Node;

public class RouteNode<T extends Node> implements Comparable<RouteNode<T>> {

    private final T current;
    private T previous;
    private int routeScore;
    private int estimatedScore;

    RouteNode(T current) {
        this(current, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    RouteNode(T current, T previous, int routeScore, int estimatedScore) {
        this.current = current;
        this.previous = previous;
        this.routeScore = routeScore;
        this.estimatedScore = estimatedScore;
    }

    public static <T extends Node> RouteNode<T> start(T origin, int computeCost) {
        return new RouteNode<>(origin, null, 0, computeCost);
    }

    @Override
    public int compareTo(RouteNode<T> other) {
        return Integer.compare(this.estimatedScore, other.estimatedScore);
    }

    public T getCurrent() {
        return current;
    }

    public T getPrevious() {
        return previous;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
    }

    public int getRouteScore() {
        return routeScore;
    }

    public void setRouteScore(int newScore) {
        this.routeScore = newScore;
    }

    public void setEstimatedScore(int newScore) {
        this.estimatedScore = newScore;
    }

}
