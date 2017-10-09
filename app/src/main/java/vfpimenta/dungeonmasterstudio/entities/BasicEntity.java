package vfpimenta.dungeonmasterstudio.entities;

public class BasicEntity {
    private String name;
    private String description;

    public BasicEntity(String name){
        this.name = name;
    }

    public BasicEntity(String name, String description) {
        this.name = name;
        this.description = description;
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
}
