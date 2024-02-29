package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.view.GameConstants;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //TODO LATER
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(GameConstants.TITLE_NAME);



        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);



    }
}
