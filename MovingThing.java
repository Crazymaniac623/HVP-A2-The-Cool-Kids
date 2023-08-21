
/**
 * Write a description of class MovingThing here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics;

public class MovingThing
{
    // instance variables - replace the example below with your own
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public MovingThing()
    {
        xPos = 50;
        yPos = 50;
        width = 50;
        height = 50;
    }

    public MovingThing(int x, int y)
    {
        xPos = x;
        yPos = y;
        width = 8;
        height = 8;
    }

    public MovingThing(int x, int y, int w, int h)
    {
        xPos = x;
        yPos = y;
        width = w;
        height = h;
    }

    public void setPos(int x, int y)
    {
        xPos = x;
        yPos = y;
    }

    public void setX(int x)
    {
        xPos = x;
    }

    public void setY(int y)
    {
        yPos = y;
    }

    public int getX()
    {
        return xPos;   //finish this method
    }

    public int getY()
    {
        return yPos;  //finish this method
    }

    public void setWidth(int w)
    {
        width = w;
    }

    public void setHeight(int h)
    {
        height = h;
    }

    public int getWidth()
    {
        return width;  //finish this method
    }

    public int getHeight()
    {
        return height;  //finish this method
    }

    public void move(String direction)
    {
        //this method will be fully implemented
        //in the sub class
        //so, leave it blank here and override in
        //the child class
    }

    public void draw(Graphics window)
    {
        //this method will be fully implemented
        //in the sub class
        //so, leave it blank here and override in
        //the child class
    }

    public String toString()
    {
        return getX() + " " + getY() + " " + getWidth() + " " + getHeight();
    }
}
