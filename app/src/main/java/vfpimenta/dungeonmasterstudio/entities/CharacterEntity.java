package vfpimenta.dungeonmasterstudio.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;

public class CharacterEntity extends BasicEntity{
    private String race;                // human, dwarf, pixie, etc
    private String role;                // druid, noble, knight, etc
    private int gold;

    public CharacterEntity(String name) {
        super(name);
    }

    public CharacterEntity(String name, String description, Bitmap image, String race, String role, int gold) {
        super(name, description, image);
        this.race = race;
        this.role = role;
        this.gold = gold;
    }

    public static CharacterEntity init(View view, Bitmap img, Resources resources) throws MissingFieldException {
        String name = ((EditText) view.findViewById(R.id.character_name)).getText().toString();
        if(name.isEmpty()){
            throw new MissingFieldException("name");
        }

        String description = ((EditText) view.findViewById(R.id.character_description)).getText().toString();
        int racePos = ((Spinner) view.findViewById(R.id.character_race)).getSelectedItemPosition();
        int rolePos = ((Spinner) view.findViewById(R.id.character_role)).getSelectedItemPosition();
        String goldStr = ((EditText) view.findViewById(R.id.character_gold)).getText().toString();

        CharacterEntity character = new CharacterEntity(name);

        if(racePos > 0){
            character.setRace(resources.getStringArray(R.array.character_race)[racePos]);
        }
        if(rolePos > 0){
            character.setRole(resources.getStringArray(R.array.character_role)[rolePos]);
        }
        if(!description.isEmpty()){
            character.setDescription(description);
        }
        if(!goldStr.isEmpty()){
            character.setGold(Integer.parseInt(goldStr));
        }
        if(img != null){
            character.setImage(img);
        }

        return character;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("<br/>");
        if(getRace() != null){
            sb.append("Race: ").append(getRace()).append("<br/>");
        }
        if(getRole() != null){
            sb.append("Role: ").append(getRole()).append("<br/>");
        }
        if(getGold() > 0){
            sb.append("Gold: ").append(getGold()).append("<br/>");
        }
        if(getDescription() != null){
            sb.append("Description: ").append(getDescription()).append("<br/>");
        }

        return sb.toString();
    }
}
