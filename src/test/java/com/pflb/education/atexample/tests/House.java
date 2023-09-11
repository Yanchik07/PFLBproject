package com.pflb.education.atexample.tests;

import java.util.List;
import java.util.Objects;

public class House {
    public Integer id;
    public Integer floorCount;
    public Double price;
    public List<ParkingPlace> parkingPlaces;

    public static class ParkingPlace {
        public Integer id;
        public Boolean isCovered;
        public Boolean isWarm;
        public Integer placesCount;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ParkingPlace that = (ParkingPlace) o;
            return  Integer.compare(that.id, id) == 0
                    && Boolean.compare(that.isCovered, isCovered) == 0
                    && Boolean.compare(that.isWarm, isWarm) == 0
                    && Integer.compare(that.placesCount, placesCount) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, isCovered, isWarm, placesCount);
        }
    }

    public House(Integer id, Integer floorCount, Double price, List<ParkingPlace> parkingPlaces) {
        this.id = id;
        this.floorCount = floorCount;
        this.price = price;
        this.parkingPlaces = parkingPlaces;
    }

    @Override
    public String toString() {
        return "House{" +
                "id=" + id +
                ", floorCount=" + floorCount +
                ", price=" + price +
                ", parkingPlaces=" + parkingPlaces +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Integer.compare(house.id, id) == 0
                && Integer.compare(house.floorCount, floorCount) == 0
                && Double.compare(house.price, price) == 0
                && parkingPlaces.equals(house.parkingPlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, floorCount, price, parkingPlaces);
    }
}
