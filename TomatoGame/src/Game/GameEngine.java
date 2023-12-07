package Game;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

/**
 * Main class where the games are coming from.
 *
 */
public class GameEngine {
    String thePlayer = null;
    private int incorrectAnswers;

    private int totalQuestions;

    // Keep track of used questions to avoid repetition
    private Set<Integer> usedQuestions = new HashSet<>();

    /**
     * Each player has their own game engine.
     * 
     * @param player
     */
    public GameEngine(String player) {
        thePlayer = player;
        totalQuestions = 0; // Initialize totalQuestions
    }

    int counter = 0;
    int score = 0;
    GameServer theGames = new GameServer();
    Game current = null;

    /**
     * Retrieves a game. This basic version only has two games that alternate.
     */
    public BufferedImage nextGame() {
        if (!isGameCompleted()) {
            // Get a new game until an unused one is found
            do {
                current = theGames.getRandomGame();
            } while (usedQuestions.contains(current.getSolution()));

            // Increment the total question count
            totalQuestions++;

            // Mark the question as used
            usedQuestions.add(current.getSolution());
        }

        return current.getImage();
    }
//    public BufferedImage nextGame() {
//        // Get a new game until an unused one is found
//        do {
//            current = theGames.getRandomGame();
//        } while (usedQuestions.contains(current.getSolution()));
//
//        // Increment the total question count only if the game is not completed
//        if (!isGameCompleted()) {
//            totalQuestions++;
//            counter++;
//        }
//
//        // Mark the question as used
//        usedQuestions.add(current.getSolution());
//
//        return current.getImage();
//    }


    public int getTotalQuestions() {
        return totalQuestions;
    }

    /**
     * Checks if the parameter i is a solution to the game URL. If so, score is
     * increased by one.
     * 
     * @param game
     * @param i
     * @return
     */
    public boolean checkSolution(int i) {
        if (i == current.getSolution()) {
            score++;
            return true;
        } else {
            // Increase the incorrect answers count
            incorrectAnswers++;
            return false;
        }
    }

    /**
     * Retrieves the score.
     * 
     * @param player
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the count of incorrect answers.
     *
     * @return
     */
    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Checks if the game is completed (for example, after a certain number of
     * questions).
     *
     * @return
     */
    public boolean isGameCompleted() {
        // Game is complete when the usedQuestion reaches 10 and the game hasn't been completed before
    	return totalQuestions == 10 && usedQuestions.size() >= 10;
    }

    /**
     * Displays a JOptionPane message showing the results of the game.
     */
    public void showGameResults() {
        int correctAnswers = score;
        int incorrectAnswers = getIncorrectAnswers();

        String message = String.format("Game Over!\nTotal Questions: %d\nCorrect Answers: %d\nIncorrect Answers: %d",
                totalQuestions, correctAnswers, incorrectAnswers);

        JOptionPane.showMessageDialog(null, message, "Game Results", JOptionPane.INFORMATION_MESSAGE);

        // Reset the game after displaying the results
        resetGame();
    }
    

    /**
     * A resetGame method
     */
    public void resetGame() {
        score = 0;
        incorrectAnswers = 0;
        usedQuestions.clear();
        totalQuestions = 0;
        counter = 0;
    }
}
