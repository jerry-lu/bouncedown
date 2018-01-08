package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Graphics;

public class DisappearPlatform extends Platform {
    private Color color;
    private int INTERVAL = 400;

    public DisappearPlatform(int px, int py, int width, int courtWidth, int courtHeight, Color color) {
        super(px, py, width, courtWidth, courtHeight, color);
        this.color = color;
    }

    private Timer timer = new Timer(INTERVAL, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            setPy(-10);
        }
    });

    @Override
    public void performAction(Square square){
        timer.start();
        super.performAction(square);
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }

}
