package nextstep.subway.path.domain;

import java.util.Objects;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Lines;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;

public class PathFinder {

    private final DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath;
    private final Lines lines;

    private PathFinder(WeightedMultigraph<Station, DefaultWeightedEdge> graph, Lines lines) {
        this.dijkstraShortestPath = new DijkstraShortestPath<Station, DefaultWeightedEdge>(graph);
        this.lines = lines;
    }

    public static PathFinder of(Lines lines) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph(DefaultWeightedEdge.class);
        lines.getLines().forEach(line -> {
            addVertex(graph, line);
            setEdgeWeight(graph, line.getSections());
        });
        return new PathFinder(graph, lines);
    }
    
    public Path findShortestPath(Station sourceStation, Station targetStation) {
        validationSameStation(sourceStation, targetStation);
        GraphPath<Station, DefaultWeightedEdge> graphPath = dijkstraShortestPath.getPath(sourceStation, targetStation);
        validationConnectedStation(graphPath);
        
        return Path.of(lines, graphPath.getVertexList(), (int) dijkstraShortestPath.getPathWeight(sourceStation, targetStation));
    }

    private static void addVertex(WeightedMultigraph<Station, DefaultWeightedEdge> graph, Line line) {
        line.getStations().forEach(graph::addVertex);
    }

    private static void setEdgeWeight(WeightedMultigraph<Station, DefaultWeightedEdge> graph, Sections sections) {
        sections.getSections().forEach(section -> graph.setEdgeWeight(graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance().getDistance()));
    }
    
    private void validationSameStation(Station sourceStation, Station targetStation) {
        if (sourceStation.equals(targetStation)) {
            throw new IllegalArgumentException("출발역과 도착역이 같습니다");
        }
    }
    
    private void validationConnectedStation(GraphPath<Station, DefaultWeightedEdge> graphPath) {
        if (Objects.isNull(graphPath)) {
            throw new IllegalArgumentException("출발역과 도착역이 연결되어있지 않습니다");
        }
    }

}
