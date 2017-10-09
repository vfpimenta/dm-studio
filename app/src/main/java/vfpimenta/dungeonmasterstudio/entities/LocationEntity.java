package vfpimenta.dungeonmasterstudio.entities;

import android.graphics.Bitmap;

import java.util.List;

public class LocationEntity extends BasicEntity {
    private List<BasicEntity> places;
    private List<BasicEntity> people;

    public LocationEntity(String name, String description, Bitmap image, List<BasicEntity> places, List<BasicEntity> people) {
        super(name, description, image);
        this.places = places;
        this.people = people;
    }

    public List<BasicEntity> getPlaces() {
        return places;
    }

    public void setPlaces(List<BasicEntity> places) {
        this.places = places;
    }

    public List<BasicEntity> getPeople() {
        return people;
    }

    public void setPeople(List<BasicEntity> people) {
        this.people = people;
    }
}
