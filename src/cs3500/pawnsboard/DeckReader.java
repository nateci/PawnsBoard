package cs3500.pawnsboard;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Represents how a file for a deck for a specific player is read.
 */
public class DeckReader {

  /**
   * Loads a deck of cards from a file for a specific player.
   *
   * @param filePath The path to the file containing card definitions
   * @param owner The color of the player who will own these cards
   * @return A list of Card objects created from the file
   * @throws IOException If the file cannot be read or is improperly formatted
   */
  public static List<Card> loadDeck(String filePath, Color owner) throws IOException {
    List<Card> deck = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        // Parse the card header line which contains name, cost, and value
        String[] parts = line.split(" ");
        if (parts.length != 3) {
          throw new IOException("Invalid card format: " + line);
        }

        String name = parts[0];
        int cost;
        int value;

        // Convert cost and value strings to integers
        try {
          cost = Integer.parseInt(parts[1]);
          value = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
          throw new IOException("Invalid cost/value format for card: " + name);
        }
        // Read the 5x5 influence grid (5 lines with 5 characters each)
        char[][] influenceGrid = new char[5][5];
        for (int i = 0; i < 5; i++) {
          line = br.readLine();
          // Validate each line of the influence grid
          if (line == null || line.length() != 5) {
            throw new IOException("Invalid influence grid format for card: " + name);
          }
          // Convert the line to a char array and store it in the grid
          influenceGrid[i] = line.toCharArray();
        }
        // Ensure the center of the influence grid is marked with 'C'
        // This represents the cell where the card will be placed
        if (influenceGrid[2][2] != 'C') {
          throw new IOException("Invalid influence grid for card " + name + ": Center must be 'C'");
        }
        // Create a new card and add it to the deck
        deck.add(new Card(name, cost, value, influenceGrid, owner));
      }
    }

    return deck;
  }
}

