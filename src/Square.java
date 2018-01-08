package src;

import java.awt.*;

/**
 * A basic game object displayed as a square of a specified color.
 */
public class Square extends GameObj {
    public static final int SIZE = 19;
    public static final int INIT_POS_X = 100;
    public static final int INIT_POS_Y = 10;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 1;

    private Color color;
    private boolean inPlay;

    /**
    * we use the superclass constructor called with the correct parameters.
    */
    public Square(int courtWidth, int courtHeight, Color color, boolean inPlay) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        this.inPlay = inPlay;
        this.color = color;
    }

    public boolean getInPlay(){
        return inPlay;
    }

    public void setInPlay(boolean b){
        inPlay = b;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy() + 1, this.getWidth() + 1, this.getHeight() + 1);
    }
}