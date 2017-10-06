package vfpimenta.dungeonmasterstudio.entities;

public class NoteEntity extends BasicEntity {
    private String condition;

    public NoteEntity(String name, String description, String condition) {
        super(name, description);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
