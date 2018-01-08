package src;

import java.awt.*;
import java.awt.Graphics;

/**
 * A game object that interacts with the Square
 **/

public abstract class Platform extends GameObj {
    
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = -1;
    public static final int height = 10;
    private Color color;
    
    
    public Platform(int px, int py, int width, int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py, width, height, courtWidth, courtHeight);
        this.color = color;
    }

    public void performAction(Square square){
        if (square != null) {
            square.setVy(this.getVy());
        }
    }

    public boolean inCourt(){
        return (super.getPy() >= -10);
    }

    public boolean hasSquare(Square square){
        if (square == null) return false;
        int squareCenter = square.getPx() + (int)(square.getWidth()/2.0);
        int leftEdge = this.getPx();
        int rightEdge = leftEdge + this.getWidth();
        return (squareCenter > leftEdge && (squareCenter < rightEdge) &&
                (square.getPy() + 20 >= (this.getPy() - 1)) && (square.getPy() + 20 <= (this.getPy() + 1)));
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}