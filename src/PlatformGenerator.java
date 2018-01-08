package src;

import java.awt.*;
import java.util.Random;


public class PlatformGenerator {
    //Adds new platform objects to the ArrayList of platforms
    private static final int COURT_WIDTH = 300;
    private static final int COURT_HEIGHT = 400;

    public PlatformGenerator(){

    }

    public Platform nextPlatform(){
        Random rnd = new Random();
        Platform p;

        int randomWidth = 50 + rnd.nextInt(50);
        int px = rnd.nextInt(220);
        int randomType = rnd.nextInt(10);

        if(randomType < 3){
            p = new DisappearPlatform(px, 390, randomWidth, COURT_WIDTH, COURT_HEIGHT, Color.GREEN);
        } else if (randomType < 4) {
            p = new DeathPlatform(px, 390, randomWidth, COURT_WIDTH, COURT_HEIGHT, Color.RED);
        } else {
            p = new SolidPlatform(px, 390, randomWidth, COURT_WIDTH, COURT_HEIGHT, Color.BLUE);
        }
        return p;
    }
}
