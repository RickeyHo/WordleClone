package Model;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Board {

    public String getAnswer() {
        if (attempts == 6){

            return answer;

        }
        return "";
    }

    private final String answer;

    private final LetterTile[][] grid = new LetterTile[6][5];

    public int getAttempts() {
        return attempts;
    }

    private int attempts = 0;
    private final HashMap<String, Boolean> fiveLetterWords;



    public Board(File file){

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
        boolean isWinner = true;
        for (int i = 0; i < 5; i++){

            //System.out.println(word[i].getCharacter().equals(answer.charAt(i)));
            //System.out.println(word[i].getCharacter());
            //System.out.println(answer.charAt(i));

            if (word[i].getCharacter().equals(answer.charAt(i))) {
                word[i].setStatus(Status.GREEN);
                charMatched[i] = true;
            } else {

                isWinner = false;

            }
        }

        System.out.println("++++++++++++++++++++++++");

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

                }
            }

            grid[attempts][f] = word[f];

        }

        attempts++;



    }



    public LetterTile[][] getGrid() {
        return grid.clone();
    }
}
