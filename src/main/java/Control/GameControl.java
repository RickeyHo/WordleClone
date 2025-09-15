package Control;

import Model.LetterTile;
import Model.Board;

import java.io.File;


public class GameControl {

    public Board board = new Board(new File("src/main/resources/wordlist.txt"));

    public void submit(String submission) {

        try {
            this.board.tryWord(submission);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
