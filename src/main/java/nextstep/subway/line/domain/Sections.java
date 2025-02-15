package nextstep.subway.line.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import nextstep.subway.station.domain.Station;

@Embeddable
public class Sections {

    @OneToMany(mappedBy = "line", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();
    
    protected Sections() {
    }

    private Sections(List<Section> sections) {
        this.sections = sections;
    }
    
    public static Sections from(List<Section> sections) {
        return new Sections(sections);
    }
    
    public List<Section> getSections() {
        return sections;
    }
    
    public List<Station> getStations() {
        if (sections.isEmpty()) {
            return Arrays.asList();
        }

        List<Station> stations = new ArrayList<>();
        Section firstSection = getFirstSection();
        stations.add(firstSection.getUpStation());
        
        Station nextStation = firstSection.getDownStation();
        stations.add(firstSection.getDownStation());
        for (int i = 0; i < sections.size()-1; i++) {
            nextStation =  getNextStation(nextStation);
            stations.add(nextStation);
        }
        
        return stations;
    }
    
    private Section getFirstSection() {
        List<Station> downStations = sections.stream()
                .map(Section::getDownStation)
                .collect(Collectors.toList());
        
        return sections.stream()
                .filter(section -> !downStations.contains(section.getUpStation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("첫 구간이 없습니다."));
    }
    
    private Station getNextStation(Station downStation) {
        return sections.stream()
                .filter(section -> downStation.equals(section.getUpStation()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("역이 연결되지 않았습니다."))
                .getDownStation();
    }
    
    void add(Section newSection) {
        checkValidStations(newSection);
        if (isSameUpStations(newSection)) {
            sections.add(newSection);
            return;
        }
        if (isSameDownStations(newSection)) {
            sections.add(newSection);
            return;
        }
        sections.add(newSection);
    }
    
    void remove(Station station) {
        checkRemovableStation(station);
        
        if (removeIfCombinableStation(station)) {
            return;
        }
        
        removeEdgeStation(station);
    }
    
    int count() {
        return sections.size();
    }
    
    Section getSectionAt(int index) {
        return getSections().get(index); 
    }
    
    private void checkValidStations(Section section) {
        if (sections.isEmpty()) {
            return;
        }

        if (isUpStationExisted(section) && isDownStationExisted(section)) {
            throw new IllegalArgumentException("이미 등록된 구간 입니다.");
        }

        if (!isUpStationExisted(section) && !isDownStationExisted(section)) {
            throw new IllegalArgumentException("등록할 수 없는 구간 입니다.");
        }
    }
    
    private boolean isSameUpStations(Section newSection) {
        if (isUpStationExisted(newSection)) {
            getSections().stream()
                .filter(section -> section.getUpStation().equals(newSection.getUpStation()))
                .findFirst()
                .ifPresent(section -> section.updateUpStation(newSection.getDownStation(), newSection.getDistance()));
            return true;
        }
        return false;
    }
    
    private boolean isSameDownStations(Section newSection) {
        if (isDownStationExisted(newSection)) {
            getSections().stream()
                .filter(section -> section.getDownStation().equals(newSection.getDownStation()))
                .findFirst()
                .ifPresent(section -> section.updateDownStation(newSection.getUpStation(), newSection.getDistance()));
            return true;
        }
        return false;
    }
    
    private boolean isUpStationExisted(Section section) {
        return getStations().stream().anyMatch(station -> station.equals(section.getUpStation()));
    }
    
    private boolean isDownStationExisted(Section section) {
        return getStations().stream().anyMatch(station -> station.equals(section.getDownStation()));
    }
    
    private void checkRemovableStation(Station station) {
        if (sections.size() <= 1) {
            throw new IllegalArgumentException("구간은 하나이상 존재해야 합니다.");
        }
        
        if (!getStations().contains(station)) {
            throw new IllegalArgumentException("노선에 등록된 역만 제거할 수 있습니다.");
        }
    }
    
    private boolean removeIfCombinableStation(Station station) {
        Section upSection = sections.stream()
                .filter(section -> station.equals(section.getDownStation()))
                .findFirst()
                .orElse(null);

        Section downSection = sections.stream()
                .filter(section -> station.equals(section.getUpStation()))
                .findFirst()
                .orElse(null);
        
        if (upSection != null && downSection != null) {
            upSection.combine(downSection);
            sections.removeIf(section -> section.equals(downSection));
            return true;
        }
        return false;
    }
    
    private void removeEdgeStation(Station station) {
        Optional<Section> edgeSection = sections.stream()
                .filter(section -> section.isContainStation(station))
                .findFirst();
        
        if (edgeSection.isPresent()) {
            sections.removeIf(section -> section.equals(edgeSection.get()));
        }
    }

    
}
