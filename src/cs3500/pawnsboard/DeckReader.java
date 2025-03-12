package cs3500.pawnsboard;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class DeckReader {
//lllllllawd jesus can you add a javadoc here i aint doin allat.
  public static List<Card> loadDeck(String filePath, Color owner) throws IOException {
    List<Card> deck = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
          throw new IOException("Invalid card format: " + line);
        }

        String name = parts[0];
        int cost;
        int value;

        try {
          cost = Integer.parseInt(parts[1]);
          value = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
          throw new IOException("Invalid cost/value format for card: " + name);
        }
        char[][] influenceGrid = new char[5][5];
        for (int i = 0; i < 5; i++) {
          line = br.readLine();
          if (line == null || line.length() != 5) {
            throw new IOException("Invalid influence grid format for card: " + name);
          }
          influenceGrid[i] = line.toCharArray();
        }
        if (influenceGrid[2][2] != 'C') {
          throw new IOException("Invalid influence grid for card " + name + ": Center must be 'C'");
        }
        deck.add(new Card(name, cost, value, influenceGrid, owner));
      }
    }

    return deck;
  }
}

