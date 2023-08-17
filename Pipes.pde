//@author Hana Parker

import java.util.Random;
import java.util.Timer;

private int topLength;
private int bottomLength;
private int hPos; //Far left of the pipes

public Pipes( int screenHeight, int screenWidth )
{
  setLengths( screenHeight );
  hPos = screenWidth;
}

//Add method to move pipes to the left every *insert time interval*

public void setLengths( int screenHeight )
{
  Random gen = new Random();
  topLength = 1 + gen.nextInt( screenHeight ); //the 1 is for a min pipLength, can be changed
  bottomLength = 1 + gen.nextInt( screenHeight - topLength ); //the 1 is for a min pipLength, can be changed
  if( topLength >= bottomLength )
    topLength -= ( topLength + bottomLength ) - screenHeight;
}

public void getLengths()
{
   getTopLength();
   getBottomLength();
}

public int getTopLength()
{
 return topLength; 
}

public int getBottomLength()
{
 return bottomLength; 
}

public int getHPos()
{
 return hPos; 
}
