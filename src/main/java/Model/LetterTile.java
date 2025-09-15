package Model;

public class LetterTile {

    private Character character;
    private Status status;

    public LetterTile(Character character) {
        this.character = character;
        this.status = Status.BLACK;
    }

    public LetterTile(Character character, Status status) {
        this.character = character;
        this.status = status;
    }


    public void setStatus(Status status) {

        this.status = status;

    }

    public Status getStatus() {
        return status;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {

        if (character == null) {
            this.character = character;
        }
    }
}
