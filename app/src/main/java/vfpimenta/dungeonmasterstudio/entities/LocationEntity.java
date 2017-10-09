package vfpimenta.dungeonmasterstudio.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;

public class LocationEntity extends BasicEntity {
    private String address;
    private List<String> people;

    public LocationEntity(String name) {
        super(name);
    }

    public LocationEntity(String name, String description, Bitmap image, String address, List<String> people) {
        super(name, description, image);
        this.address = address;
        this.people = people;
    }

    public static LocationEntity init(View view, Bitmap img, Resources resources) throws MissingFieldException {
        String name = ((EditText) view.findViewById(R.id.location_name)).getText().toString();
        if (name.isEmpty()) {
            throw new MissingFieldException("name");
        }

        String description = ((EditText) view.findViewById(R.id.location_description)).getText().toString();

        LocationEntity location = new LocationEntity(name);

        if (!description.isEmpty()) {
            location.setDescription(description);
        }
        if (img != null) {
            location.setImage(img);
        }

        return location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("<br/>");
        if(!getPeople().isEmpty()){
            int idx = 0;
            for(String person : people){
                if(idx == 0) sb.append("People: ").append(person);
                else sb.append(", ").append(person);
                idx++;
            }
        }
        if(getDescription() != null){
            sb.append("Description: ").append(getDescription()).append("<br/>");
        }

        return sb.toString();
    }
}
