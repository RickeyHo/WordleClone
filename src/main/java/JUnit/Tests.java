package JUnit;
import Model.Board;
import Model.LetterTile;
import Model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    Board board;

    @BeforeEach
    void initialize(){

        board = new Board(new File("src/main/resources/wordlist.txt"), "ferry");

    }
    @Test
    void invalidWordsCheck(){

        assertThrows(RuntimeException.class, () -> board.tryWord("thewe"));

    }
    @Test
    void wordsSizeCheck(){

        assertThrows(RuntimeException.class, () -> board.tryWord("thee"));
        assertThrows(RuntimeException.class, () -> board.tryWord("cheese"));


    }
    @Test
    void validWordsCheck(){

        try {
            board.tryWord("there");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void checkStatusGreen(){

        try {

            board.tryWord("marry");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LetterTile[][] grid = board.getGrid();


        assertEquals(grid[0][2].getStatus(), Status.GREEN);
        assertEquals(grid[0][3].getStatus(), Status.GREEN);
        assertEquals(grid[0][4].getStatus(), Status.GREEN);

    }
    @Test
    void checkStatusBlack(){

        try {

            board.tryWord("marry");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LetterTile[][] grid = board.getGrid();
        assertEquals(Status.BLACK, grid[0][0].getStatus());
        assertEquals(Status.BLACK, grid[0][1].getStatus());

    }
    @Test
    void checkStatusYellow(){

        try {

            board.tryWord("radio");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LetterTile[][] grid = board.getGrid();
        assertEquals(Status.YELLOW, grid[0][0].getStatus());

    }
    @Test
    void checkEdgeCase(){

        try {

            board.tryWord("dairy");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LetterTile[][] grid = board.getGrid();
        assertEquals(Status.GREEN, grid[0][3].getStatus());
        assertEquals(Status.GREEN, grid[0][4].getStatus());

    }
    @Test
    void checkEdgeCase2(){

        try {

            board.tryWord("geese");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LetterTile[][] grid = board.getGrid();
        assertEquals(Status.GREEN, grid[0][1].getStatus());
        assertEquals(Status.BLACK, grid[0][2].getStatus());
        assertEquals(Status.BLACK, grid[0][4].getStatus());

    }

}
