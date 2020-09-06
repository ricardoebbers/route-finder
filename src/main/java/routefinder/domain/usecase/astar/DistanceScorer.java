package routefinder.domain.usecase.astar;

import routefinder.domain.entity.Node;
import routefinder.domain.entity.Scorer;
import routefinder.domain.repository.DistanceRepository;

import java.util.List;

public class DistanceScorer<T extends Node> implements Scorer<T> {

    private final DistanceRepository<T> distanceRepository;

    public DistanceScorer(DistanceRepository<T> distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    @Override
    public int computeCost(T origin, T destination) {
        return distanceRepository.getDistance(origin, destination);
    }

    @Override
    public int getTotalDistance(List<T> route) {
        return distanceRepository.getTotalDistance(route);
    }

}
