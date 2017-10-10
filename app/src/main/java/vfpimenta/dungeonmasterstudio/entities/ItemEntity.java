package vfpimenta.dungeonmasterstudio.entities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;
import vfpimenta.dungeonmasterstudio.util.IOHandler;

public class ItemEntity extends BasicEntity{
    private String type;
    private String rarity;
    private boolean attuned;

    public ItemEntity(String name) {
        super(name);
    }

    public static ItemEntity init(Context context, View view, Bitmap img, Resources resources) throws MissingFieldException {
        String name = ((EditText) view.findViewById(R.id.item_name)).getText().toString();
        if (name.isEmpty()) {
            throw new MissingFieldException("name");
        }

        String description = ((EditText) view.findViewById(R.id.item_description)).getText().toString();
        int typePos = ((Spinner) view.findViewById(R.id.item_type)).getSelectedItemPosition();
        int rarePos = ((Spinner) view.findViewById(R.id.item_rarity)).getSelectedItemPosition();
        boolean requiresAttunement = ((RadioButton) view.findViewById(R.id.attuned_true)).isChecked();


        ItemEntity item = new ItemEntity(name);

        if(typePos > 0){
            item.setType(resources.getStringArray(R.array.item_type)[typePos]);
        }
        if(rarePos > 0){
            item.setRarity(resources.getStringArray(R.array.item_rarity)[rarePos]);
        }
        item.setAttuned(requiresAttunement);
        if(!description.isEmpty()){
            item.setDescription(description);
        }
        if (img != null) {
            IOHandler.storeImage(context, img, item.getId());
        }

        return item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public boolean isAttuned() {
        return attuned;
    }

    public void setAttuned(boolean attuned) {
        this.attuned = attuned;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("<br/>");
        if(getType() != null){
            sb.append("Type: ").append(getType()).append("<br/>");
        }
        if(getRarity() != null){
            sb.append("Rarity: ").append(getRarity()).append("<br/>");
        }
        sb.append("Require attunement: ");
        if(isAttuned()){
            sb.append("yes");
        } else{
            sb.append("no");
        }
        sb.append("<br/>");
        if(getDescription() != null){
            sb.append("Description: ").append(getDescription()).append("<br/>");
        }

        return sb.toString();
    }
}
