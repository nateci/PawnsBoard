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
To play the game, you need a properly formatted deck (.config) file. Here’s a simple JUnit test to demonstrate how you can set up and start a game in Java:

```java
  @Test
  public void testGameEndsAfterConsecutivePasses() {
    // Set up the board and players
    Board board = new Board(3, 5);
    Player redPlayer = new Player(Color.RED, java.util.List.of(
            new Card("Security", 1, 2, new char[][]{
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'I', 'C', 'I', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'}
            }, Color.RED)));

    Player bluePlayer = new Player(Color.BLUE, java.util.List.of(
            new Card("Security", 1, 2, new char[][]{
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'I', 'C', 'I', 'X'},
                    {'X', 'X', 'I', 'X', 'X'},
                    {'X', 'X', 'I', 'X', 'X'}
            }, Color.BLUE)));

    // Create input with "pass" command for both players
    String simulatedInput = "pass\npass\n";
    ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(testIn);

    Game game = new Game(board, redPlayer, bluePlayer);

    // Play the game (process both "pass" commands and end game)
    game.play();

    // Verify the game is over
    assertTrue(game.isGameOver());
  }
}
```

## **Key Components**

### **Game Loop (`Game.java`)**
- Manages player turns & game progression.
- Ensures valid moves & handles user input,
- Calls `switchTurn()` after every valid move.
- Ends when both players pass consecutively.
- Determines the winner based on final row scores.

### **Board (`Board.java`)**
- A rectangular grid where the game takes place.
- Contains cells that store pawns or cards.
- Provides logic for placing cards & applying influence.
- Will prevent invalid moves (out-of-bounds, insufficient pawns, etc.).

### **Players (`Player.java`)**
- Each player has:
  - A color (Red or Blue).
  - A deck of cards.
  - A hand drawn from the deck.
- Handles card placement, ensuring only valid moves are played.

### **Cards (`Card.java`)**
- Each card has:
  - A name (e.g.: "Security").
  - A cost (1-3 pawns required to place).
  - A value (for row scoring).
  - A 5x5 influence grid (determines how the board is affected).
- Influence adds pawns or converts opponent's pawns.

### **View (`View.java`)**
- Handles text-based rendering of:
  - The board (pawns, cards, row scores).
  - Player hands (cards available for play).
  - Game prompts and messages (turn announcements, invalid move messages, etc.).
  - Final game results.

### **Deck Reader (`DeckReader.java`)**
- Reads deck configurations from a file.
- Ensures cards are correctly formatted before adding them to the player's deck.

---

### **Key Subcomponents**
### **Cells (`Cell.java`)**
- Stores pawns or a card.
- Tracks ownership (Red or Blue).
- Determines if a card can be placed.

### **Card Influence**
- Each card projects its influence onto nearby cells based on its 5x5 influence grid.
- Influence effects:
  - If a cell is empty: Adds a pawn for the current player.
  - If a cell has opponent pawns: Converts one of them.
  - If a cell already has a card: No change.

### **Scoring System**
- Each row is scored separately.
- The player with the higher row-score wins that row.
- Total score = sum of won row-scores.
- Tied rows give no points to either player.

---

## **Source Organization** (UPDATED FOR PART 2)
**`src/cs3500/`** (_Main_)
- `PawnsBoard.java`

**`src/cs3500/pawnsboard/`** (_Model_)
- `Game.java`
- `Board.java`
- `Player.java`
- `Card.java`
- `Cell.java`
- `DeckReader.java`
- `DeckReaderTest.java`
- `MockCell.java`
- `MockReadOnlyPawnsBoardModel.java`
- `PawnsBoardCard.java`
- `PawnsBoardCell.java`
- `ReadOnlyPawnsBoardCard.java`
- `ReadOnlyPawnsBoardCell.java`
- `ReadOnlyPawnsBoardModel.java`

**`src/cs3500/strategy/`** (_Strategy_)
- `Strategy.java`
- `Move.java`
- `FillFirstStrategy.java`
- `MaximizeRowScoreStrategy.java`
- `ControlTheBoardStrategy.java`
- `MinimaxStrategy.java`
- `ChainedStrategy.java`
- `TieBreaker.java`

**`src/cs3500/pawnsboard/view/`** (_View_)
- `PawnsBoardTextualView.java`
- `PawnsBoardViewImpl.java`
- `BoardPanel.java`
- `HandPanel.java`
- `PawnsBoardView.java`
- `PawnsBoardViewController.java`
- `PawnsBoardViewControllerImpl.java`
- `ReadOnlyBoardWrapper.java`

**`test/cs3500/pawnsboard/model/`** (_Model Tests_)
- `BoardTest.java`
- `GameTest.java`
- `PlayerTest.java`
- `InvalidMovesTest.java`

**`test/cs3500/pawnsboard/strategy/`** (_Strategy Tests_)
- `FillFirstStrategyTest.java`
- `MaximizeRowScoreStrategyTest.java`
- `ControlTheBoardStrategyTest.java`
- `MinimaxStrategyTest.java`
- `ChainedStrategyTest.java`
- `TieBreakerTest.java`
- `StrategyTranscriptGenerator.java`

**`test/cs3500/pawnsboard/view/`** (_View Tests_)
- `BoardPanelTest.java`
- `HandPanelTest.java`
- `PawnsBoardViewImplTest.java`
- `ViewTest.java`

**`docs/`** (_Config files_)
- `deck.config`
---

## **Changes for Part 2**

### **Strategic Computer Players**

We introduced four new classes implementing the `Strategy` interface:

- **`FillFirstStrategy`**  
  Scans cards in hand from left to right and board cells from top-left to bottom-right. Chooses the first valid `(card, row, col)` move and stops. If no valid move exists, the player passes.

- **`MaximizeRowScoreStrategy`**  
  Traverses rows from top to bottom. In each row, attempts to find the first `(card, row, col)` move that improves the player’s score to be **greater than or equal to** the opponent's. If no such move exists in any row, the player passes.

- **`ControlTheBoardStrategy`** *(Extra Credit)*  
  Evaluates moves based on how many total board cells would be owned after placing a card. Chooses the move that maximizes ownership. Ties are broken by **topmost**, then **leftmost**, then **lowest card index**.

- **`MinimaxStrategy`** *(Extra Credit)*  
  Simulates each possible move and evaluates the number of valid responses left for the opponent, minimizing their future options. Requires simulating the opponent’s strategy internally.

We also added:

- **`ChainedStrategy`** – Combines multiple strategies in priority order.
- **`TieBreaker`** – Resolves move ties based on consistent rules.

All strategies were thoroughly unit-tested using mocks. Strategy decision transcripts are saved in:
- `strategy-transcript-first.txt` (for **FillFirst**)
- `strategy-transcript-score.txt` (for **MaximizeRowScore**)

---

### **Testing Strategies with Mocks**

To isolate and validate strategy behavior, we created:

- **`MockReadOnlyPawnsBoardModel`**  
  A controllable simulation of the game model that overrides legality checks, scoring methods, and logs which cells/rows are accessed.

- **`MockCell`**  
  A simplified board cell that returns static pawn ownership and values.

These mocks allowed us to:
- Simulate legal and illegal moves
- Trigger specific row score outcomes
- Confirm traversal and tie-breaking behavior
- Generate strategy transcripts for submission

---

###  **Visual View and Event Handling**

We implemented a complete GUI for Pawns Board using **Java Swing**:

- **`PawnsBoardViewImpl`** – Renders the full window using a read-only model
- **`PawnsBoardPanel`** – Custom `JPanel` for drawing the board and cards
- **`StubController`** – Handles clicks and key events for testing interactivity

The view supports:
- Click-to-select on cards and board cells
- Visual highlighting for selected items
- Keyboard input:  
  - **Enter** → Confirm move  
  - **Space Bar** → Pass turn
- Correct mirrored influence grids for Blue player
- Dynamic resizing

---

### **Screenshots**

Four screenshots of a 5-row by 7-column game are included:
- `start.png` – Start of game
- `red-turn.png` – Red player's turn with a selected card and cell
- `blue-turn.png` – Blue's first turn with mirrored cards
- `midgame.png` – Non-trivial midgame state with several cards played
---


### **Note for Running the JAR**

To run the program via the provided JAR file, ensure that a file named `deck.config` is available in the working directory. This configuration file contains the starting cards for both players and is required for the game to load and launch correctly.

The `deck.config` file **is already included** with this submission.

---

**I hope you enjoy playing our Pawns Board :) !**
