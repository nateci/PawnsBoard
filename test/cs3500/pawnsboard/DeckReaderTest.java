package cs3500.pawnsboard;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DeckReaderTest {

  @Test
  public void testLoadDeck() throws IOException {
    List<Card> deck = DeckReader.loadDeck("docs/deck.config", Color.RED);
    assertNotNull(deck);
    assertFalse(deck.isEmpty());
    Card firstCard = deck.get(0);
    assertEquals("Security", firstCard.getName());
    assertEquals(1, firstCard.getCost());
    assertEquals(2, firstCard.getValue());
    char[][] grid = firstCard.getInfluenceGrid();
    assertEquals('C', grid[2][2]);
  }
}
