package nextstep.subway.line.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nextstep.subway.BaseEntity;
import nextstep.subway.station.domain.Station;

@Entity
public class Line extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String name;
    
    private String color;

    @Embedded
    private Sections sections = new Sections();

    protected Line() {
    }

    private Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    private Line(String name, String color, Station upStation, Station downStation, Distance distance) {
        this.name = name;
        this.color = color;
        addSection(Section.of(this, upStation, downStation, distance));
    }
    
    public static Line of(String name, String color) {
        return new Line(name, color);
    }
    
    public static Line of(String name, String color, Station upStation, Station downStation, Distance distance) {
        return new Line(name, color, upStation, downStation, distance);
    }
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Sections getSections() {
        return sections;
    }

    public void update(Line line) {
        this.name = line.getName();
        this.color = line.getColor();
    }
    
    public void addSection(Section section) {
        sections.add(section);
    }
    
    public void removeSection(Station station) {
        sections.remove(station);
    }
    
    public List<Station> getStations() {
        return sections.getStations();
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(name, line.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
