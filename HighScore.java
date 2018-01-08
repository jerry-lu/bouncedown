package src;

import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class HighScore {
    //Map that maps a score to a name
    private Map<Integer, String> scoreMap;

    //local state
    private String fileName;

    public HighScore(String fileName) {
        this.fileName = fileName;
        scoreMap = new TreeMap<>();
    }

    public Map<Integer, String> getHighScores() {
        try {
            BufferedReader readFile = new BufferedReader (new FileReader (fileName));
            String textLine, name;

            while ((textLine = readFile.readLine())!= null) {
                String[] arr = textLine.split("; ");
                if(arr[0].length() >= 16){
                    name = arr[0].substring(0, 15);
                } else {
                    name = arr[0];
                }
                int score = Integer.parseInt(arr[1]);
                scoreMap.put(score, name);
            }
            readFile.close();
            return scoreMap;
        } catch (Exception e) {
            e.printStackTrace();
            return new TreeMap<Integer, String>();
        }
    }

    public static void writeHighScores(Map<Integer, String> scoreMap) {
        try {
            FileWriter writeFile = new FileWriter("files/HighScores.txt");
            List<Integer> keys = new ArrayList<>(scoreMap.keySet());
            Collections.sort(keys);
            Collections.reverse(keys);

            for (int i : keys) {
                String name = scoreMap.get(i);
                String score = Integer.toString(i);
                String nextLine = name + "; " + score + "\n";
                writeFile.write(nextLine);
            }
            writeFile.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


}
