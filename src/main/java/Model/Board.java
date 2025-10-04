package Model;
import org.junit.jupiter.api.function.Executable;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Board {

    private boolean isWinner;

    public String getAnswer() {
        if (attempts == 6){

            return answer;

        }
        return "";
    }

    private String answer;

    private final LetterTile[][] grid = new LetterTile[6][5];

    public LetterTile[][] getKeyboard() {
        return keyboard.clone();
    }

    private final LetterTile[][] keyboard = {
            { new LetterTile('Q', Status.GREY), new LetterTile('W', Status.GREY), new LetterTile('E', Status.GREY), new LetterTile('R', Status.GREY), new LetterTile('T', Status.GREY), new LetterTile('Y', Status.GREY), new LetterTile('U', Status.GREY), new LetterTile('I', Status.GREY), new LetterTile('O', Status.GREY), new LetterTile('P', Status.GREY) },
            { new LetterTile(' ', Status.GREY), new LetterTile('A', Status.GREY), new LetterTile('S', Status.GREY), new LetterTile('D', Status.GREY), new LetterTile('F', Status.GREY), new LetterTile('G', Status.GREY), new LetterTile('H', Status.GREY), new LetterTile('J', Status.GREY), new LetterTile('K', Status.GREY), new LetterTile('L', Status.GREY), new LetterTile(' ', Status.GREY) },
            { new LetterTile(' ', Status.GREY), new LetterTile(' ', Status.GREY), new LetterTile('Z', Status.GREY), new LetterTile('X', Status.GREY), new LetterTile('C', Status.GREY), new LetterTile('V', Status.GREY), new LetterTile('B', Status.GREY), new LetterTile('N', Status.GREY), new LetterTile('M', Status.GREY), new LetterTile(' ', Status.GREY), new LetterTile(' ', Status.GREY) }
    };



    public int getAttempts() {
        return attempts;
    }

    private int attempts = 0;
    private final HashMap<String, Boolean> fiveLetterWords;


    public Board(File file){

        this.isWinner = false;

        this.fiveLetterWords = new HashMap<>();

        String word;
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                word = reader.nextLine();
                fiveLetterWords.put(word, true);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object[] wordsArr = fiveLetterWords.keySet().toArray();

        this.answer = ((String) wordsArr[Math.abs(new Random().nextInt() % fiveLetterWords.keySet().size())]).toUpperCase();
    }

    public Board(File file, String answer){

        this(file);
        this.answer = answer;

    }


    public Executable tryWord(String submission) throws Exception {
        if (attempts >= 6){

            throw new RuntimeException("Out of attempts.");

        }
        if (submission.length() != 5){

            throw new RuntimeException("Word must be 5 letters in length.");

        }
        if (!fiveLetterWords.containsKey(submission.toLowerCase())){

            throw new RuntimeException("Word not in word list.");

        }


        LetterTile[] word = new LetterTile[5];

        for (int i = 0; i < submission.length(); i++){

            word[i] = new LetterTile(submission.charAt(i));

        }


        boolean[] charMatched = new boolean[5];
        this.isWinner = true;
        for (int i = 0; i < 5; i++){

            //System.out.println(word[i].getCharacter().equals(answer.charAt(i)));
            //System.out.println(word[i].getCharacter());
            //System.out.println(answer.charAt(i));

            if (word[i].getCharacter().equals(answer.charAt(i))) {
                word[i].setStatus(Status.GREEN);
                charMatched[i] = true;

                for (LetterTile[] row: keyboard){

                    for (LetterTile tile: row){

                        if (tile.getCharacter().equals(answer.charAt(i))){

                            tile.setStatus(Status.GREEN);
                            break;

                        }

                    }

                }

            } else {

                this.isWinner = false;

            }
        }


        for (int f = 0; f < 5; f++) {

            if (word[f].getStatus() != Status.GREEN){
                Boolean characterFound = false;
                for (int x = 0; x < 5; x++) {

                    //System.out.println("Guess - " + word[f].getCharacter());
                    //System.out.println("Answer - " + answer.charAt(x));

                    //System.out.println(!charMatched[x]);
                    if (word[f].getCharacter().equals(answer.charAt(x)) && !charMatched[x]) {

                        characterFound = true;
                        charMatched[x] = true;
                        break;

                    }

                }
                if (characterFound) {

                    word[f].setStatus(Status.YELLOW);
                    for (LetterTile[] row: keyboard){

                        for (LetterTile tile: row){

                            if (tile.getCharacter().equals(word[f].getCharacter()) && tile.getStatus() != Status.GREEN){

                                tile.setStatus(Status.YELLOW);
                                break;

                            }

                        }

                    }

                } else {

                    for (LetterTile[] row: keyboard){

                        for (LetterTile tile: row){

                            if (tile.getCharacter().equals(word[f].getCharacter()) && tile.getStatus() != Status.GREEN){

                                tile.setStatus(Status.BLACK);
                                break;

                            }

                        }

                    }


                }
            }

            grid[attempts][f] = word[f];

        }

        attempts++;


        return null;
    }



    public LetterTile[][] getGrid() {
        return grid.clone();
    }

    public boolean isWinner() {
        return isWinner;
    }
    
}
