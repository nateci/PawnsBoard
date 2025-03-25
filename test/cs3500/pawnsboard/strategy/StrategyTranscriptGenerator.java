package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.MockReadOnlyPawnsBoardModel;
import cs3500.strategy.FillFirstStrategy;
import cs3500.strategy.MaximizeRowScoreStrategy;
import cs3500.strategy.Strategy;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility to generate transcripts for strategy behavior and write to a file.
 */
public class StrategyTranscriptGenerator {

  public static void main(String[] args) {
    // Change this to run FillFirstStrategy or MaximizeRowScoreStrategy
    generateTranscript(new FillFirstStrategy(), "strategy-transcript-first.txt");
    generateTranscript(new MaximizeRowScoreStrategy(),
            "strategy-transcript-score.txt");
  }

  private static void generateTranscript(Strategy strategy, String outputFileName) {
    MockReadOnlyPawnsBoardModel model = new MockReadOnlyPawnsBoardModel(3,
            5, Color.RED);

    // Run strategy
    strategy.chooseMoves(model, Color.RED);

    // Output transcript to file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
      for (String line : model.strategyTranscript) {
        writer.write(line);
        writer.newLine();
      }
      System.out.println("Transcript written to " + outputFileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
