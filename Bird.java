
/**
 * Write a description of class Bird here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

import javax.swing.*;

public class Bird extends MovingThing
{
    // instance variables - replace the example below with your own
    private int height;
    private Image image;

    /**
     * Constructor for objects of class Bird
     */
    public Bird( int screenHeight )
    {
        height = screenHeight / 2;
    }
    
    public void gravity()
    {
        height++;
    }
    
    public void up()
    {
        height -= 15;
    }
    
    public int getHeight()
    {
        return height;
    }
}
