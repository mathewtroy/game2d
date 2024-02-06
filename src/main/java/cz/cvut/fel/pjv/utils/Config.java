package cz.cvut.fel.pjv.utils;

import cz.cvut.fel.pjv.view.GamePanel;

import java.io.*;

public class Config {

    GamePanel gp;


    public Config(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Saves the game configuration to a file.
     */
    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // Music volume
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SE volume
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();


            //
            bw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the game configuration from a file.
     */
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // Music volume
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            // SE volume
            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);


            //
            br.close();


        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }

}
