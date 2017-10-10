package vfpimenta.dungeonmasterstudio.entities;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;

import vfpimenta.dungeonmasterstudio.R;
import vfpimenta.dungeonmasterstudio.exceptions.MissingFieldException;

public class NoteEntity extends BasicEntity {
    private String condition;
    private String action;

    public NoteEntity(String name) {
        super(name);
    }

    public NoteEntity(String name, String description, Bitmap image, String condition, String action) {
        super(name, description, image);
        this.condition = condition;
        this.action = action;
    }

    public static NoteEntity init(View view, Bitmap img) throws MissingFieldException {
        String name = ((EditText) view.findViewById(R.id.note_name)).getText().toString();
        if (name.isEmpty()) {
            throw new MissingFieldException("name");
        }

        String condition = ((EditText) view.findViewById(R.id.note_condition)).getText().toString();
        String action = ((EditText) view.findViewById(R.id.note_action)).getText().toString();
        String description = ((EditText) view.findViewById(R.id.note_description)).getText().toString();

        NoteEntity note = new NoteEntity(name);

        if(!condition.isEmpty()){
            note.setCondition(condition);
        }
        if(!description.isEmpty()){
            note.setDescription(description);
        }
        if(!action.isEmpty()){
            note.setAction(action);
        }
        if (img != null) {
            note.setImage(img);
        }

        return note;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName()).append("<br/>");
        if(getCondition() != null){
            sb.append("Condition: ").append(getCondition()).append("<br/>");
        }
        if(getDescription() != null){
            sb.append("Description: ").append(getDescription()).append("<br/>");
        }
        if(getAction() != null){
            sb.append("Action: ").append(getAction()).append("<br/>");
        }

        return sb.toString();
    }
}
