package vfpimenta.dungeonmasterstudio.entities;

import android.graphics.Bitmap;

public class ItemEntity extends BasicEntity{
    public ItemEntity(String name, String description, Bitmap image) {
        super(name, description, image);
    }

    @Override
    public String toHtml() {
        return null;
    }
}
