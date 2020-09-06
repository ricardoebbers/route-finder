package routefinder.domain.entity;

import java.util.List;

public interface Scorer<T extends Node> {

    int computeCost(T origin, T destination);

    int getTotalDistance(List<T> route);

}
