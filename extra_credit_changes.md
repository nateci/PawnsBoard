## 🔧 Changes for Extra Credit Features

The following files were **created** or **modified** to implement all 3 extra credit features.

---

### 🆕 Files Created

#### `ColorScheme.java`
- An interface defining theming behavior for contrast modes:
  - `getCellColor(...)`
  - `getTextColor(...)`
  - `getScoreCircleColor(...)`
  - `getHighlightColor()`

#### `DefaultColorScheme.java`
- Implements the normal mode color scheme:
  - Light red/blue themes, black text, cyan highlight
  - Used as the default view for all components

#### `HighContrastColorScheme.java`
- Implements high contrast mode:
  - Pure black cells, white text, red/cyan pawns, yellow highlight
  - Ensures maximum accessibility

---

### 🧾 Files Modified

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
