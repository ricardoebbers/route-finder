package routefinder.domain.entity;

import java.util.Objects;

public class City implements Node {

    private final String abbreviation;

    public City(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return abbreviation.equals(city.abbreviation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation);
    }

    @Override
    public String getId() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return "City{" +
                "abbreviation='" + abbreviation + '\'' +
                '}';
    }

}
