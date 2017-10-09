package vfpimenta.dungeonmasterstudio.entities;

import android.graphics.Bitmap;

public class BasicEntity {
    private String name;            // required field
    private String description;
    private Bitmap image;

    public BasicEntity(String name){
        this.name = name;
    }

    public BasicEntity(String name, String description, Bitmap image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
