package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.List;
import java.util.*;


/**
 * GameScreen
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameScreen extends JPanel {

    // the state of the game logic
    private Square square; // the Black Square, keyboard control
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel scoreLabel; // current score text
    
    // Game constants
    private static final int COURT_WIDTH = 300;
    private static final int COURT_HEIGHT = 400;
    private static final int SQUARE_VELOCITY = 3;
    private static final int DEFAULT_SQUARE_VY = 2;

    //counter that controls the delay between creating platforms
    private int count = 0;
    
    //tracks score
    private int score = 0;

    //ArrayList storing platforms on the screen
    private List<Platform> platforms = new ArrayList<>();

    //Map that maps a score to a name
    private TreeMap<Integer, String> scoreMap;

    // adds platforms to the game
    private PlatformGenerator generator;

    // Update interval for timer, in milliseconds
    private static final int INTERVAL = 8;
    
    GameScreen(JLabel status, JLabel scoreLabel) {

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer object triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single time step.

        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    square.setVx(SQUARE_VELOCITY);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    square.setVx(-SQUARE_VELOCITY);
                }
            }
            public void keyReleased(KeyEvent e) {
                square.setVx(0);
            }
        });

        HighScore h = new HighScore("files/HighScores.txt");
        scoreMap = new TreeMap<>(h.getHighScores());
        generator = new PlatformGenerator();
        this.status = status;
        this.scoreLabel = scoreLabel;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        square = new Square(COURT_WIDTH, COURT_HEIGHT, Color.BLACK, true);
        //snitch = new Circle(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
        platforms.clear();
        platforms.add(generator.nextPlatform());
        playing = true;
        status.setText("Running...");
        score = 0;
        count = 0;
        scoreLabel.setText("Score: 0");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    private void tick() {
        if (playing) {
            // advance the square
            square.move();

            //move platforms
            for(Platform p : platforms){
                p.moveNoClip();
            }

            //removes the platforms not in the court
            platforms.removeIf((Platform p) -> !p.inCourt());

            //check if ball is on a platform. If it is, perform the action given by that specific platform's type
            boolean onPlatform = false;
            for(Platform p : platforms){
                if (p.hasSquare(square)){
                    p.performAction(square);
                    onPlatform = true;
                }
            }

            count++;

            //handle square speed when not on platforms
            if(!onPlatform){
                square.setVy(DEFAULT_SQUARE_VY);
            }
                
            //iterate score and update score
            if (count % 80 == 0){
                score++;
                scoreLabel.setText("Score: " + score);
            }
            
            //generate platforms
            if(count % 90 == 0){
                platforms.add(generator.nextPlatform());
                count = 0;
            }

            // check for the game end conditions
            if (square.getPy() >= 379 || square.getPy() == 0 || !square.getInPlay()) {
                playing = false;
                status.setText("You lose!");
                String inputValue;

                if((scoreMap.size() < 3) || score > scoreMap.firstKey()){
                    inputValue = JOptionPane.showInputDialog("You got a High Score! Write your name here");
                    if (inputValue != null) addHighScore(score, inputValue);
                }
            }
          
            // update the display
            repaint();
        }
    }

    //adds a high score and calls the HighScore class to write the new high score to a text file
    private void addHighScore(int score, String name){
        if(name.length() > 16){
            name = name.substring(0, 15);
        }
        scoreMap.put(score, name);
        if(scoreMap.size() > 5){
            int lowest = scoreMap.firstKey();
            scoreMap.remove(lowest);
        }
        HighScore.writeHighScores(scoreMap);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        square.draw(g);
        for(Platform p : platforms){
            p.draw(g);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}