package Control;

import Model.LetterTile;
import Model.Board;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GameControl {

    public Board board = new Board(new File("src/main/resources/wordlist.txt"));

    public void submit(String submission) {

        try {
            this.board.tryWord(submission);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void saveGame(){

        try (PrintWriter printWriter = new PrintWriter(new File("savedGames/previousGame.json"))){
            Gson gson = new GsonBuilder().create();
            gson.toJson(board, printWriter);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public void loadPrevGame() throws Exception{

        Scanner scanner = new Scanner(new File("savedGames/previousGame.json"));
        String jsonContent = scanner.nextLine();
        Gson gson = new Gson();
        this.board = gson.fromJson(jsonContent, (this.board).getClass());


    }


}
