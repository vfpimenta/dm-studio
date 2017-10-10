package vfpimenta.dungeonmasterstudio.entities;

import android.graphics.Bitmap;

public class NoteEntity extends BasicEntity {
    private String condition;

    public NoteEntity(String name, String description, Bitmap image, String condition) {
        super(name, description, image);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toHtml() {
        return null;
    }
}
