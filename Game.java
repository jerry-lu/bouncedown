package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Bounce Down");
        frame.setLocation(300, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Score Label
        final JLabel scoreLabel = new JLabel("Score: 0");
        control_panel.add(scoreLabel);

        // High Score Leaderboard Panel
        final JPanel score_panel = new JPanel();
        score_panel.setPreferredSize(new Dimension(130,300));
        frame.add(score_panel, BorderLayout.EAST);

        // Instructions panel
        final JPanel instructionPanel = new JPanel();
        frame.add(instructionPanel, BorderLayout.WEST);

        // Main playing area
        final GameScreen court = new GameScreen(status, scoreLabel);
        frame.add(court, BorderLayout.CENTER);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        //Button that allows player to view the high scores
        final JButton scoreButton = new JButton("See High Scores");
        scoreButton.setPreferredSize(new Dimension(120,60));
        scoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean wasPlaying = court.playing;
                court.playing = false;
                HighScore h = new HighScore("files/HighScores.txt");
                Map<Integer, String> highScores = h.getHighScores();
                List<Integer> scoreList = new ArrayList<Integer>(highScores.keySet());
                Collections.sort(scoreList);
                Collections.reverse(scoreList);
                String highScoreText = "";

                if (highScores.size() > 0) {
                    for (int i : scoreList) {
                        highScoreText = highScoreText + "\t" + highScores.get(i) + ":\t" + Integer.toString(i) + "\n";
                    }
                    JOptionPane.showMessageDialog(frame, highScoreText, "High Scores", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "There are currently no high scores!",
                            "High Scores", JOptionPane.PLAIN_MESSAGE);
                }
                court.requestFocusInWindow();
                if (wasPlaying) court.playing = true;
            }
        });
        score_panel.add(scoreButton);

        //Button that allows player to view instructions
        final JButton instructionButton = new JButton("See Instructions");
        instructionButton.setPreferredSize(new Dimension(120,60));
        instructionPanel.add(instructionButton);
        instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean wasPlaying = court.playing;
                court.playing = false;
                JOptionPane.showMessageDialog(frame,
                        "Controls: Move the square left and right using the arrow keys.\n" +
                                "Scoring: Your score increases over time.\n\n" +
                                "Caution: the green platforms disappear, and avoid the red platforms with spikes!\n" +
                                "Don't let the square touch the top or bottom of the screen!",
                                "Instructions", JOptionPane.PLAIN_MESSAGE);
                if(wasPlaying) court.playing = true;
                court.requestFocusInWindow();
            }
        });

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}