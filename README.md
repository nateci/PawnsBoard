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

## **Source Organization** (UPDATED FOR PART 4)
**`src/cs3500/`** (_Main_)
- `PawnsBoard.java`
- `ReadOnlyBoardWrapper.java`

- **`src/cs3500/pawnsboard/`** (_Controller_)
- `MachinePlayerController.java`
- `ModelFeatures.java`
- `PawnsBoardViewController.java`
- `PawnsBoardViewControllerImpl.java`
- `PlayerActionListener.java`
- `PlayerController.java`

- **`src/cs3500/adapter/`** (_Adapter_)
- `CardAdapter.java`
- `CellAdapter.java`
- `ControllerAdapter.java`
- `GameModelAdapter.java`
- `ModelAdapter.java`
- `ModelListenerAdapter.java`
- `PlayerAdapter.java`
- `PlayerIntAdapter.java`

- **`src/cs3500/pawnsboard/provider/controller/`** (_Provider Controller_)
- `ModelStatusListener.java`
- `PlayerActionListener.java`

- **`src/cs3500/pawnsboard/provider/model/`** (_Provider Model_)
- `Board.java`
- `Card.java`
- `Cell.java`
- `GameModel.java`
- `MoveStrat.java`
- `PlayerInt.java`
- `ReadOnlyGameModel.java`

- **`src/cs3500/pawnsboard/provider/view/`** (_Provider View_)
- `PawnsBoardView.java`

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
- 
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
- **`BoardPanel`** – Custom `JPanel` for drawing the board and cards
- **`HandPanel`** – Handles clicks and key events for testing interactivity

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


### **Note for Running the JAR (UPDATED FOR PART 4)**

To run the program via the provided JAR file, ensure that a file named `deck.config` is available in the working directory. This configuration file contains the starting cards for both players and is required for the game to load and launch correctly. Make sure you provide 3 args. (I.e. : java -jar pawnsboard.jar docs/deck.config provider-human human). **NOTE: OUR DECKLOADER WILL SORT DECKS WITH ONE DECK INPUTTED.**



The `deck.config` file **is already included** with this submission.


## **Changes for Part 3**

### **Fully Playable Game with GUI Controllers**

In Part 3, we completed the game’s functionality by connecting human and machine players to the model via controllers and made the game fully playable using the graphical user interface.

### **Controller Addition**

- Created **`PawnsBoardViewControllerImpl`**, which connects the model and the view for human players.
  - Implements `PawnsBoardViewController`, `ModelFeatures`, and `PlayerController`.
  - Handles user actions like selecting a card or board cell, confirming a move, or passing a turn.
  - Ensures moves are only allowed during the player’s turn and invalid moves are properly rejected.
  - Displays move feedback via status messages, rather than disruptive popups.

- **`MachinePlayerController`** was created:
  - Now listens for turn notifications from the model.
  - Uses strategies to pick moves automatically and play them during its turn.

### **Event Handling and Game Loop**

- The game loop is now controlled by the model (`Game.java`) through turn-based notifications.
- Each controller listens to model updates and ensures only the active player can act.
- Controllers register with the model before the game begins to ensure the first player gets the turn.

### **Two-Player GUI Windows**

- Each player now has their own GUI window (`PawnsBoardViewImpl`), complete with controller logic.
- `main()` initializes both views and controllers, and spaces them apart on screen.

### **Improved UX Feedback**

- Titles of each player's window update to reflect turn status (e.g. "Red Player - Your Turn").
- Game results are clearly shown at the end in a single popup and in the window title/status.

### **Configurable Game Mode**

- In the `PawnsBoard` main class, booleans `redIsHuman` and `blueIsHuman` toggle between human and AI.
- Easy to switch between Human vs Human, Human vs AI, or AI vs AI matchups.

## Part 4: View Integration & Provider Adapter

### Features Integrated from Provider View

We successfully integrated the provider group’s `PawnsBoardView` for use as **Player 2 (Blue)** in our game. This view runs in a separate window and uses their existing GUI and internal selection handling. The following features from the provider view are fully functional:

- GUI rendering of board and hand
- Card selection via mouse
- Board cell selection via mouse
- Move confirmation using the **`C` key**
- Turn refresh and highlighting
- Final game results display

### Adapter Implementation & Fixes

To support the provider’s view without modifying their code, we created the following adapter classes:

- `GameModelAdapter` – Implements the provider’s `GameModel` interface and forwards game state from our internal model.
- `ControllerAdapter` – Acts as both `PlayerActionListener` and `ModelStatusListener`, translating provider view events into model actions.
- `ModelListenerAdapter` – Bridges our model’s `ModelFeatures` events back to the provider’s listeners (e.g. `turnChanged`, `gameOver`).

To resolve compatibility, we:
- Pulled the selected card and cell directly from the provider view in `confirmMove()`, because their view does **not emit `cardSelected` or `cellSelected` events**.
- Attached key bindings in `ControllerAdapter` to ensure `'c'`, `'p'`, and `'q'` trigger confirm, pass, and quit.
- Ensured turn-specific model listeners are notified correctly from our game loop.

### Known Limitations

The following features were **not supported or out-of-scope** due to provider implementation constraints:
- Visual indicators for invalid moves are not shown (only printed to console)

### Files Changed for Customers

For our customer group (who used our model and view), we made the following changes to our codebase:

- **Extracted and shared clean model interfaces** (`ReadOnlyPawnsBoardModel`, `Card`, `Cell`, etc.) to allow customers to access board state
- Ensured no model implementation classes were exposed
- Documented known limitations and assumptions in `README`

These changes were needed to decouple our model logic from internal representation and allow others to build adapters or strategy components without needing full implementation access.

## Part 4: View Integration & Provider Adapter

### Features Integrated from Provider View

We successfully integrated the provider group’s `PawnsBoardView` for use as **Player 2 (Blue)** in our game. This view runs in a separate window and uses their existing GUI and internal selection handling. The following features from the provider view are fully functional:

- GUI rendering of board and hand
- Card selection via mouse
- Board cell selection via mouse
- Move confirmation using the **`C` key**
- Turn refresh and highlighting
- Final game results display

### Adapter Implementation & Fixes

To support the provider’s view without modifying their code, we created the following adapter classes:

- `GameModelAdapter` – Implements the provider’s `GameModel` interface and forwards game state from our internal model.
- `ControllerAdapter` – Acts as both `PlayerActionListener` and `ModelStatusListener`, translating provider view events into model actions.
- `ModelListenerAdapter` – Bridges our model’s `ModelFeatures` events back to the provider’s listeners (e.g. `turnChanged`, `gameOver`).

To resolve compatibility, we:
- Pulled the selected card and cell directly from the provider view in `confirmMove()`, because their view does **not emit `cardSelected` or `cellSelected` events**.
- Attached key bindings in `ControllerAdapter` to ensure `'c'`, `'p'`, and `'q'` trigger confirm, pass, and quit.
- Ensured turn-specific model listeners are notified correctly from our game loop.

### Known Limitations

The following features were **not supported or out-of-scope** due to provider implementation constraints:
- Provider view does not expose internal Swing components for testability
- Visual indicators for invalid moves are not shown (only printed to console)

### Files Changed for Customers

For our customer group (who used our model and view), we made the following changes to our codebase:

- **Extracted and shared clean model interfaces** (`ReadOnlyPawnsBoardModel`, `Card`, `Cell`, etc.) to allow customers to access board state
- Ensured no model implementation classes were exposed
- Moved all `view` components into their own package for clarity
- Documented known limitations and assumptions in `README`

These changes were needed to decouple our model logic from internal representation and allow others to build adapters or strategy components without needing full implementation access.
---

**I hope you enjoy playing our (and our partners) Pawns Board :) !**
