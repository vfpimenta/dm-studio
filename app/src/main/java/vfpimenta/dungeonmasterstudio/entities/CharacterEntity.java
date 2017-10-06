package vfpimenta.dungeonmasterstudio.entities;

public class CharacterEntity extends BasicEntity{
    private Reference reference;

    public CharacterEntity(String name, String description, Reference reference) {
        super(name, description);
        this.reference = reference;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
