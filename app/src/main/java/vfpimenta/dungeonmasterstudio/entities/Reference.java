package vfpimenta.dungeonmasterstudio.entities;

public class Reference {
    private String bookName;
    private String characterName;
    private int page;

    public Reference(String bookName, String characterName, int page) {
        this.bookName = bookName;
        this.characterName = characterName;
        this.page = page;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return getCharacterName()+". "+getBookName()+", p."+getPage();
    }
}
