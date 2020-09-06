package routefinder;

import routefinder.adapter.FileIOAdapter;
import routefinder.adapter.impl.StringToFileIOAdapter;
import routefinder.domain.entity.City;
import routefinder.domain.entity.Graph;
import routefinder.domain.entity.Scorer;
import routefinder.domain.repository.DistanceRepository;
import routefinder.domain.repository.impl.DistanceRepositoryImpl;
import routefinder.domain.usecase.ShortestPath;
import routefinder.domain.usecase.astar.DistanceGraph;
import routefinder.domain.usecase.astar.DistanceScorer;
import routefinder.domain.usecase.astar.RouteFinder;

public class Main {

    private static final DistanceRepository<City> distanceRepository = new DistanceRepositoryImpl<>();
    private static final Graph<City> graph = new DistanceGraph<>(distanceRepository);
    private static final Scorer<City> scorer = new DistanceScorer<>(distanceRepository);
    private static final ShortestPath<City> shortestPath = new RouteFinder<>(graph, scorer);
    private static final FileIOAdapter fileIOAdapter = new StringToFileIOAdapter(shortestPath);

    public static void main(String[] args) {
        fileIOAdapter.loadDistances("trechos.txt");
        String result = fileIOAdapter.calculateDistances("encomendas.txt");
        fileIOAdapter.writeToOutput("rotas.txt", result);
    }

}
