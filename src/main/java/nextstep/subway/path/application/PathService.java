package nextstep.subway.path.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.subway.line.application.LineService;
import nextstep.subway.line.domain.Lines;
import nextstep.subway.path.domain.FareCalculator;
import nextstep.subway.path.domain.Path;
import nextstep.subway.path.domain.PathFinder;
import nextstep.subway.path.dto.PathResponse;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;

@Service
@Transactional
public class PathService {
    
    private final LineService lineService;
    private final StationService stationService;
    
    public PathService(LineService lineService, StationService stationService) {
        this.lineService = lineService;
        this.stationService = stationService;
    }

    @Transactional(readOnly = true)
    public PathResponse findShortestPath(Long sourceId, Long targetId, int age) {
        Station sourceStation = stationService.findById(sourceId);
        Station targetStation = stationService.findById(targetId);
        
        Lines lines = Lines.of(lineService.findLines());
        PathFinder pathFinder = PathFinder.of(lines);
        Path path = pathFinder.findShortestPath(lines.getAllStations(), sourceStation, targetStation);
        int fare = FareCalculator.calculator(path.getDistance(), path.getSurcharge(), age);
        
        return PathResponse.of(path, fare);
    }

}
