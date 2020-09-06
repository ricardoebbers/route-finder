package routefinder.domain.usecase.astar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import routefinder.domain.entity.City;
import routefinder.domain.entity.Graph;
import routefinder.domain.entity.Scorer;
import routefinder.domain.exception.InvalidDistanceException;
import routefinder.domain.exception.NodeNotFoundException;
import routefinder.domain.exception.RouteNotFoundException;
import routefinder.domain.repository.DistanceRepository;
import routefinder.domain.repository.impl.DistanceRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RouteFinderTest {

    private RouteFinder<City> routeFinder;
    private DistanceRepository<City> repository;

    @BeforeEach
    void setUp() {
        repository = new DistanceRepositoryImpl<>();
        Graph<City> graph = new DistanceGraph<>(repository);
        Scorer<City> scorer = new DistanceScorer<>(repository);
        routeFinder = new RouteFinder<>(graph, scorer);
    }

    @Test
    void findRoute_givenThereIsARouteFromOriginToDestination_thenReturnsARouteContainingBoth() {
        // given
        City origin = new City("A");
        City destination = new City("B");
        routeFinder.addRoute(origin, destination, 1);
        // when
        List<City> route = routeFinder.findRoute(origin, destination);
        // then
        assertEquals(2, route.size());
        assertEquals(origin, route.get(0));
        assertEquals(destination, route.get(1));
    }

    @Test
    void findRoute_givenThereIsAnIndirectRoute_thenReturnsTheShortestRoute() {
        // given
        City origin = new City("A");
        City destination = new City("B");
        City closerIntermediate = new City("C");
        City distantIntermediate = new City("D");
        routeFinder.addRoute(origin, distantIntermediate, 10);
        routeFinder.addRoute(distantIntermediate, destination, 10);
        routeFinder.addRoute(origin, closerIntermediate, 5);
        routeFinder.addRoute(closerIntermediate, destination, 5);
        // when
        List<City> route = routeFinder.findRoute(origin, destination);
        // then
        assertEquals(3, route.size());
        assertEquals(origin, route.get(0));
        assertEquals(closerIntermediate, route.get(1));
        assertEquals(destination, route.get(2));
    }

    @Test
    void findRoute_givenTheOriginIsTheSameAsTheDestination_thenReturnsARouteContainingOnlyTheOrigin() {
        // given
        City origin = new City("A");
        City destination = new City("A");
        // when
        List<City> route = routeFinder.findRoute(origin, destination);
        // then
        assertEquals(1, route.size());
        assertEquals(origin, route.get(0));
        assertEquals(destination, route.get(0));
    }

    @Test
    void findRoute_givenThereIsNoRouteFromOriginToDestination_thenThrowsRouteNotFound() {
        // given
        City origin = new City("A");
        City other = new City("O");
        City destination = new City("B");
        routeFinder.addRoute(origin, other, 1);
        routeFinder.addRoute(destination, other, 1);
        // when && then
        assertThrows(RouteNotFoundException.class, () ->
                routeFinder.findRoute(origin, destination)
        );
    }

    @Test
    void findRoute_givenNullParameters_thenThrowsRouteNotFound() {
        // when && then
        assertThrows(RouteNotFoundException.class, () ->
                routeFinder.findRoute(null, null)
        );
    }

    @Test
    void findRoute_givenThereAreCircularReferences_thenStillFindsTheShortestRoute() {
        // given
        City origin = new City("A");
        City other = new City("O");
        City destination = new City("B");
        routeFinder.addRoute(origin, origin, 1);
        routeFinder.addRoute(origin, other, 100);
        routeFinder.addRoute(other, destination, 50);
        // when
        List<City> route = routeFinder.findRoute(origin, destination);
        // then
        assertEquals(3, route.size());
        assertEquals(origin, route.get(0));
        assertEquals(other, route.get(1));
        assertEquals(destination, route.get(2));
    }

    @Test
    void addRoute_givenTwoDistinctCities_thenCreatesAConnectionBetweenThem() {
        // given
        City origin = new City("A");
        City destination = new City("B");
        // when
        routeFinder.addRoute(origin, destination, 1);
        // then
        assertTrue(repository.getConnections(origin.getId()).contains(destination));
    }

    @Test
    void addRoute_givenTwoDistinctCities_thenDontCreateATwoWayConnection() {
        // given
        City origin = new City("A");
        City destination = new City("B");
        // when
        routeFinder.addRoute(origin, destination, 1);
        // then
        assertFalse(repository.getConnections(destination.getId()).contains(origin));
    }

    @Test
    void addRoute_givenARouteToTheSameCity_thenCreatesACircularConnection() {
        // given
        City origin = new City("A");
        // when
        routeFinder.addRoute(origin, origin, 1);
        // then
        assertTrue(repository.getConnections(origin.getId()).contains(origin));
    }

    @Test
    void addRoute_givenNegativeDistance_thenThrowsException() {
        // given
        City origin = new City("A");
        // when && then
        assertThrows(InvalidDistanceException.class, () ->
                routeFinder.addRoute(origin, origin, -1)
        );
    }

    @Test
    void getNode_givenExistingNode_thenReturnsIt() {
        // given
        City origin = new City("A");
        routeFinder.addRoute(origin, origin, 1);
        // when
        routeFinder.getNode("A");
        // then
        assertTrue(repository.getConnections(origin.getId()).contains(origin));
    }

    @Test
    void getNode_givenNodeThatDoesntHaveRoutes_thenThrows() {
        // when && then
        assertThrows(NodeNotFoundException.class, () ->
                routeFinder.getNode("A")
        );
    }

    @Test
    void getTotalDistance_givenARoute_thenSumsAllDistancesInRelevantRoute() {
        // given
        City origin = new City("A");
        City other = new City("O");
        City intermediary = new City("I");
        City destination = new City("B");
        routeFinder.addRoute(origin, intermediary, 12);
        routeFinder.addRoute(origin, other, 3);
        routeFinder.addRoute(intermediary, destination, 34);
        // when
        List<City> route = routeFinder.findRoute(origin, destination);
        int totalDistance = routeFinder.getTotalDistance(route);
        // then
        assertEquals(12 + 34, totalDistance);
    }

    @Test
    void getTotalDistance_givenRouteWithOnlyOrigin_thenReturnsZero() {
        // given
        City origin = new City("A");
        // when
        List<City> route = routeFinder.findRoute(origin, origin);
        int totalDistance = routeFinder.getTotalDistance(route);
        // then
        assertEquals(0, totalDistance);
    }

    @Test
    void getTotalDistance_givenRouteWithInvalidNodes_thenReturnsIntMaxValue() {
        // given
        List<City> route = new ArrayList<>();
        route.add(new City("U"));
        route.add(new City("Z"));
        // when
        int totalDistance = routeFinder.getTotalDistance(route);
        // then
        assertEquals(Integer.MAX_VALUE, totalDistance);
    }

}
