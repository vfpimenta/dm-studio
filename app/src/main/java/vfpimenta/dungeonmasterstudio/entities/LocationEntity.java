package vfpimenta.dungeonmasterstudio.entities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;
import vfpimenta.dungeonmasterstudio.util.IOHandler;

public class LocationEntity extends BasicEntity {
    private String address;
    private List<String> people;

    public LocationEntity(String name) {
        super(name);
    }

    public static LocationEntity init(Context context, View view, Bitmap img) throws MissingFieldException {
        String name = ((EditText) view.findViewById(R.id.location_name)).getText().toString();
        if (name.isEmpty()) {
            throw new MissingFieldException("name");
        }

        List<String> people = new ArrayList<>();
        LinearLayout peopleContainer = view.findViewById(R.id.people_container);
        for (int i = 0; i < peopleContainer.getChildCount(); i++) {
            Spinner person = (Spinner) peopleContainer.getChildAt(i);
            people.add(person.getSelectedItem().toString());
        }
        String address = ((EditText) view.findViewById(R.id.location_address)).getText().toString();
        String description = ((EditText) view.findViewById(R.id.location_description)).getText().toString();

        LocationEntity location = new LocationEntity(name);

        if (!address.isEmpty()) {
            location.setAddress(address);
        }
        if (!people.isEmpty()) {
            location.setPeople(people);
        }
        if (!description.isEmpty()) {
            location.setDescription(description);
        }
        if (img != null) {
            IOHandler.storeImage(context, img, location.getId());
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

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("<br/>");
        if(getAddress() != null){
            sb.append("Address: ").append(getAddress()).append("<br/>");
        }
        if(getPeople() != null && !getPeople().isEmpty()){
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
