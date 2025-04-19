## Changes for Extra Credit Features

The following files were **created** // **modified** to implement the extra credit features.

---

### Files Created

#### `ColorScheme.java`
- An interface defining theming behavior for contrast modes:
  - `getCellColor(...)`
  - `getTextColor(...)`
  - `getScoreCircleColor(...)`
  - `getHighlightColor()`

#### `DefaultColorScheme.java`
- Implements the normal mode color scheme:
  - Light red/blue themes, black text, cyan highlight
  - Used as the default view for all components (that way we can impl a contrast scheme)

#### `HighContrastColorScheme.java`
- Implements high contrast mode:
  - Pure black cells, white text, red/cyan pawns, yellow highlight
---

### Files Modified

#### `BoardPanel.java`
- Added a `ColorScheme colorScheme` field with a default
- Added `setColorScheme(...)` method to inject new schemes at runtime
- Updated:
  - `drawBoard(...)` to use `colorScheme.getCellColor(...)`
  - `drawCellContents(...)` to use `colorScheme.getTextColor(...)`, `getScoreCircleColor(...)`
  - `drawRowScores(...)` now uses `Color.BLACK` always for visibility on white margin

#### `HandPanel.java`
- Added a `ColorScheme colorScheme` field with default
- Added `setColorScheme(...)` method to apply styling dynamically
- Updated:
  - `drawPlayerIndicator(...)` to use `getTextColor(...)`
  - `drawCard(...)` to use `getCellColor(...)` for background and `getTextColor(...)` for all text

#### `PawnsBoardViewImpl.java`
- Created `addContrastToggle(...)` method that:
  - Adds a checkbox to toggle contrast mode at runtime
  - Calls `boardPanel.setColorScheme(...)` and `handPanel.setColorScheme(...)` accordingly
- Attached the toggle to the existing `legendPanel`
- Set default color scheme on both panels at construction

#### `Board.java`
- Updated `placeCard(...)` and `applyInfluence(...)` logic to support new influence types:
  - `'U'` (Upgrade): increases value modifier on the target cell
  - `'D'` (Devalue): decreases value modifier on the target cell
- Handles card removal when a cell's effective value drops to 0 or below:
  - Removes the card
  - Leaves pawns in the cell matching the card's original cost
  - Resets the modifier for that cell
- `calculateRowScores(...)` was modified to factor in value modifiers (`getValueModifier()`)

#### `Cell.java`
- Added support for value-based influence via:
  - `int valueModifier` — tracks net influence from Upgrade (U) and Devalue (D)
  - `boolean modifierFrozen` — locks the modifier after a card is removed from the cell
- methods changed:
  - `getValueModifier()` — returns the cumulative upgrade/devalue influence
  - `removeCardAndConvertToPawns()` — handles card deletion when value ≤ 0, drops pawns, and freezes the modifier
  - modified `influenceBoard(...)` — applies `I`, `U`, or `D` logic based on influence type:
    - `'I'` increases pawn count or converts ownership
    - `'U'` increases future card value (if not frozen)
    - `'D'` decreases future card value (if not frozen)
- Ensures modifiers stack, persist, and reset when required

