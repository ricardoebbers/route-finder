package routefinder.domain.entity;

import java.util.Objects;

public class Path<T> {

    private final T origin;
    private final T destination;

    public Path(T origin, T destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public static <T extends Node> Path<T> of(T origin, T destination) {
        return new Path<>(origin, destination);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path<T> path = (Path<T>) o;
        return origin.equals(path.origin) &&
                destination.equals(path.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination);
    }

    @Override
    public String toString() {
        return "Path{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }

}
