# Pawns Board

## Overview
Pawns Board is a strategic two-player board game where players compete to control a grid using pawns and cards with unique effects. Each player, Red and Blue, will take turns placing cards on a rectangular board to influence nearby cells, convert opponent's pawns, and secure higher row scores. The game ends when both players pass consecutively, and the winner is determined by the total score respectively to conquered (or won) rows.

### **Key Assumptions**
- The game is strictly two-player.
- The board size must meet the following conditions:
  - Rows: Must be equal to 3.
  - Columns: Must be equal to 5.
- Each player's deck is read from a configuration file and contains only two copies of each card.
- Cards influence surrounding cells, either adding pawns or converting opponent’s pawns.
- Pawns cannot be moved, only influenced by cards.
- The game runs in a text-based format.

---

##  **Quick Start**
To play the game, you need a properly formatted deck configuration file. Here’s an code snippet example of how you can set up and start a game in Java:

```java
public class PawnsBoard {
    public static void main(String[] args) {
        // Load decks for both players
        List<Card> redDeck = DeckReader.loadDeck("docs/deck.config", Color.RED);
        List<Card> blueDeck = DeckReader.loadDeck("docs/deck.config", Color.BLUE);

        // Initialize players
        Player redPlayer = new Player(Color.RED, redDeck);
        Player bluePlayer = new Player(Color.BLUE, blueDeck);

        // Set up the board and start the game
        Board board = new Board(3, 5);
        Game game = new Game(board, redPlayer, bluePlayer);
        game.play();
    }
}
```

## **Key Components**

### **Game Loop (`Game.java`)**
- Manages **player turns** and **game progression**.
- Ensures **valid moves** and **handles user input**.
- Calls `switchTurn()` after every valid move.
- Ends when **both players pass** consecutively.
- Determines the **winner** based on final row scores.

### **Board (`Board.java`)**
- A **rectangular grid** where the game takes place.
- Contains **cells** that store **pawns or cards**.
- Provides logic for **placing cards** and **applying influence**.
- **Prevents invalid moves** (out-of-bounds, insufficient pawns, etc.).

### **Players (`Player.java`)**
- Each player has:
  - A **color** (Red or Blue).
  - A **deck** of cards.
  - A **hand** drawn from the deck.
- Handles **card placement**, ensuring only **valid moves** are played.

### **Cards (`Card.java`)**
- Each card has:
  - A **name** (e.g., "Security").
  - A **cost** (1-3 pawns required to place).
  - A **value** (affects row scoring).
  - A **5x5 influence grid** (determines how the board is affected).
- Influence **adds pawns or converts opponent's pawns**.

### **View (`View.java`)**
- Handles **text-based rendering** of:
  - **The board** (pawns, cards, row scores).
  - **Player hands** (cards available for play).
  - **Game prompts and messages** (turn announcements, invalid move messages, etc.).
  - **Final game results**.

### **Deck Reader (`DeckReader.java`)**
- Reads deck configurations from a **file**.
- Ensures **cards are correctly formatted** before adding them to the player's deck.

---

### **Key Subcomponents**
### **Cells (`Cell.java`)**
- Stores **pawns or a card**.
- Tracks **ownership** (Red or Blue).
- Determines if a **card can be placed**.

### **Card Influence**
- Each card **projects its influence** onto nearby cells based on its **5x5 influence grid**.
- Influence effects:
  - **If a cell is empty:** Adds a **pawn** for the current player.
  - **If a cell has opponent pawns:** Converts **one** of them.
  - **If a cell already has a card:** No change.

### **Scoring System**
- **Each row is scored separately.**
- The player with the **higher row-score wins that row**.
- Total score = **sum of won row-scores**.
- **Tied rows give no points** to either player.

---

## **Source Organization**
**'`src/cs3500/`**(_Main_)*
- `PawnsBoard.java` → Main method for running the game PawnsBoard.

**`src/cs3500/pawnsboard/`** (_Model_)*
- `Game.java` → Controls game flow.
- `Board.java` → Represents the game grid.
- `Player.java` → Manages player actions and cards.
- `Card.java` → Defines card behavior and influence.
- `Cell.java` → Represents a single space on the board.
- `DeckReader.java` → Loads decks from files.

**'`src/cs3500/pawnsboard/view/`**(_View_)*
- `PawnsBoardTextualView.java` → Textual view for PawnsBoard.

**`test/cs3500/pawnsboard/`** *(Model tests)*
- `BoardTest.java` → Ensures board initialization and logic work correctly.
- `GameTest.java` → Tests game mechanics, turn switching, and scoring.
- `PlayerTest.java` → Validates player actions.
- `ViewTest.java` → Checks correct text output for board, hands, and results.
- `InvalidMovesTest.java` → Ensures invalid moves are properly rejected.

**`test/cs3500/pawnsboard/view/`** *(View tests)*
- `ViewTest.java` → Ensures PawnsBoardTextualView is functioning correctly.

**`docs/`** *(Config files)*
- `deck.config` → Contains the list of available cards for players.

---

**I hope you enjoy playing Pawns Board!**
