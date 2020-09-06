package routefinder.adapter.impl;

import routefinder.adapter.FileIO;
import routefinder.adapter.FileIOAdapter;
import routefinder.domain.entity.City;
import routefinder.domain.usecase.ShortestPath;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StringToFileIOAdapter implements FileIOAdapter {

    private final ShortestPath<City> shortestPath;
    private final FileIO fileIO;

    public StringToFileIOAdapter(ShortestPath<City> shortestPath) {
        this.shortestPath = shortestPath;
        this.fileIO = new FileIO();
    }

    public StringToFileIOAdapter(ShortestPath<City> shortestPath, Path basePath) {
        this.shortestPath = shortestPath;
        this.fileIO = new FileIO(basePath);
    }

    @Override
    public void loadDistances(String filename) {
        fileIO.readLines(filename).forEach(this::loadDistance);
    }

    @Override
    public String calculateDistances(String filename) {
        return fileIO.readLines(filename).stream()
                .map(this::calculateDistance)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void writeToOutput(String filename, String result) {
        fileIO.write(filename, result);
    }

    private void loadDistance(String line) {
        String[] split = line.split(" ");
        City origin = new City(split[0]);
        City destination = new City(split[1]);
        int distance = Integer.parseInt(split[2]);
        shortestPath.addRoute(origin, destination, distance);
    }

    private String calculateDistance(String line) {
        String[] split = line.split(" ");
        City origin = shortestPath.getNode(split[0]);
        City destination = shortestPath.getNode(split[1]);
        System.out.printf("Looking for route from %s to %s.%n", split[0], split[1]);
        List<City> route = shortestPath.findRoute(origin, destination);
        int totalDistance = shortestPath.getTotalDistance(route);
        return route.stream().map(City::getId).collect(Collectors.joining(" ")) + " " + totalDistance;
    }

}
