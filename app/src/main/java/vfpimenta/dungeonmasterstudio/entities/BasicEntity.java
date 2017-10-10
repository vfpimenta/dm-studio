package vfpimenta.dungeonmasterstudio.entities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;

import vfpimenta.dungeonmasterstudio.util.IOHandler;

public abstract class BasicEntity {
    private int _id;                // key for image retrieval
    private String name;            // required field
    private String description;

    public BasicEntity(String name){
        this._id = this.hashCode();
        this.name = name;
    }

    protected String getId() {
        return _id+"";
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

    public Bitmap getImage(Context context) {
        return IOHandler.loadImage(context, getId());
        //Uri imageUri = Uri.parse(getImageStr());
        //return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
    }

    public abstract String toHtml();
}
