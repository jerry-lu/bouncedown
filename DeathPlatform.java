package src;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.Color;

public class DeathPlatform extends Platform {

    private Color color;
    public static final String IMG_FILE = "files/spikes.png";
    private static BufferedImage img;
    public static final int height = 5;

    public DeathPlatform(int px, int py, int width, int courtWidth, int courtHeight, Color color) {
        super(px, py, width, courtWidth, courtHeight, color);
        this.color = color;

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void performAction(Square square) {
        if (square != null) {
            square.setInPlay(false);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawImage(img, this.getPx(), this.getPy() - height, this.getWidth(), height, null);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), height);
    }
}
