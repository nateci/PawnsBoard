package cs3500.pawnsboard.model;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import cs3500.pawnsboard.Card;
import cs3500.pawnsboard.DeckReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the DeckReader class.
 * This test ensures that deck configuration files are correctly loaded
 * and that the cards are properly parsed into the expected format.
 */
public class DeckReaderTest {
  @Test
  public void testLoadDeck() throws IOException {
    // Load the deck from the specified configuration file
    List<Card> deck = DeckReader.loadDeck("docs/deck.config", Color.RED);
    // Ensure the deck is not null
    assertNotNull(deck, "Deck should not be null.");
    // Ensure the deck is not empty
    assertFalse(deck.isEmpty(), "Deck should contain at least one card.");
    // Retrieve the first card from the deck
    Card firstCard = deck.get(0);
    // Verify the first card's properties
    assertEquals("Security", firstCard.getName(),
            "First card should be 'Security'.");
    assertEquals(1, firstCard.getCost(),
            "Security card should have a cost of 1.");
    assertEquals(2, firstCard.getValue(),
            "Security card should have a value of 2.");
    // Retrieve the influence grid of the first card
    char[][] grid = firstCard.getInfluenceGrid();
    // Ensure the center of the influence grid is 'C' (center)
    assertEquals('C', grid[2][2],
            "The center of the influence grid should be 'C'.");
  }
}
