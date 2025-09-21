package Model;
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

    private final String answer;

    private final LetterTile[][] grid = new LetterTile[6][5];

    public LetterTile[][] getKeyboard() {
        return keyboard.clone();
    }

    private final LetterTile[][] keyboard = {
        { new LetterTile('Q'), new LetterTile('W'), new LetterTile('E'), new LetterTile('R'), new LetterTile('T'), new LetterTile('Y'), new LetterTile('U'), new LetterTile('I'), new LetterTile('O'), new LetterTile('P') },
        { new LetterTile(' '), new LetterTile('A'), new LetterTile('S'), new LetterTile('D'), new LetterTile('F'), new LetterTile('G'), new LetterTile('H'), new LetterTile('J'), new LetterTile('K'), new LetterTile('L'), new LetterTile(' ') },
        { new LetterTile(' '), new LetterTile(' '), new LetterTile('Z'), new LetterTile('X'), new LetterTile('C'), new LetterTile('V'), new LetterTile('B'), new LetterTile('N'), new LetterTile('M'), new LetterTile(' '), new LetterTile(' ') }
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


    public void tryWord(String submission) throws Exception {
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

                            if (tile.getCharacter().equals(word[f].getCharacter())){

                                tile.setStatus(Status.YELLOW);
                                break;

                            }

                        }

                    }

                }
            }

            grid[attempts][f] = word[f];

        }

        attempts++;



    }



    public LetterTile[][] getGrid() {
        return grid.clone();
    }

    public boolean isWinner() {
        return isWinner;
    }
    
}
