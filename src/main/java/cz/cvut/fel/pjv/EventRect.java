package cz.cvut.fel.pjv;

import java.awt.*;

/**
 * The EventRect class represents rectangular regions
 * in the game world where events can occur.
 * It extends the Rectangle class to provide functionality for event handling.
 */
public class EventRect extends Rectangle {

    int eventRectDefaultX, eventRectDefaultY;
    boolean eventDone = false;

}
