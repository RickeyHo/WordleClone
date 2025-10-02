# Project Report

## Design Decisions

### Architecture

The model stores the game state, and enforces the rules of the game. It enforces when the words inputed by the player are not 6 letters, or when the word is not considered as a valid word.

The view is solely responsible for the GUI of the game. It takes the game state from the model and displays them in a way that is consistent with how the original Wordle game does it. No game logic is stored by the view.

The controller handles the the passing of user inputs from the GUI to the model for validation. On top of that, it handles saving of the game, and the reopening of the game from the previous save.

I created the class LetterTile in order to abstract away the process of keeping track of the status of each individual letter when implementing the Board class in the Model. So the board simply stores a 2D array of LetterTile instances, and when the board determines how the letter should be categorized, it uses the set status method of LetterTile to determine how it should be displayed to the user later, when the GUI also uses the get status method.

I used Swing to simplify the process, especially since Wordle is not a game which necessitates complex animations. I was also able to override methods of the Table class when creating subclasses WordleTable and KeyboardTable so that I can directly pass in a 2D array of class LetterTile and it will automatically color the cells the proper color, as well as setting the text to the correct character.

### Data Structures

Game state is represented by a 2D array of the custom class LetterTile. The LetterTile has two fields, one field for the character, and another field for the status. It also has getter and setter methods for those fields, which enforce encapsulation, as the setter method for the Character will not allow for the user to change the character after it has already been set.

This structure was chosen because it closely aligns with how the original Wordle game was implemented. The custom structure LetterTile also greatly simplifies the implementation, as it prevents the need to have tow separate 2D arrays, one for the character and another for the status. 

### Algorithms

On startup the game hashes each individual word in a word list and stores it into a hashmap. If it is found on the hashtable and the word is six letters long, it is accepted.

The Model determines the status of the LetterTile by iterating through the letters of the input word twice. The first time it checks if the letter matches the corresponding letter on the correct answer, and if it does it sets the status of the letter to green. The second time it checks every letter to see if any instance of that letter appears on the original word, if it does, and it has not already been set as green, it will be set to yellow. The default status of the letter is black so the algorithm ends there. 

On startup the big O is O(n), but subsequent moves made by the user are O(1).


## Challenges Faced
1. **Challenge 1:** The first challenge I was running into was overcoming the edge cases when it came to setting the status of the words. Initially, I had mistakenly not checked for a green status before setting the status of a LetterTile instance to yellow, causing errors, because it kept overwriting the decision made by the first iteration.

   - **Solution:** It was overcome by checking if the LetterTile was set to green, and having it skip that instance if it was.
   

## What We Learned
The OOP concept of overriding was the most reinforced when implementing the custom WordleTable class as a subclass of Table, as it made possible directly passing in the 2D array of custom class LetterTile and having it automatically display the contents correctly. This made the implementation much cleaner. 
The design pattern that was discovered was the ability to reuse code and implementations. As I implemented the Keyboard, I realized I could just reuse much of the same code that went into the WordleView, but instead have the 2D array correspond to keys on the keyboard.
The first challenge faced had me realize the importance of testing for edge cases with JUnit when validating the algorithms involved. 

## If We Had More Time
- Reimplementation of the GUI in JavaFX to enable animations that are not necessary but would help make it more polished like the original by the NYT.
- Brute forcing the original Wordle NYT in order to determine their internal word list for the game, so that the playing experience can be exactly the same.
