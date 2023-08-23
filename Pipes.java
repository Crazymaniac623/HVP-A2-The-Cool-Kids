import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Pipes
{
	private int topLength;
	private int bottomLength;
	private int hPos; //Far left position of the pipes
	
	//Pipes screenHeight, screenWidth Constructor
	public Pipes( int screenHeight, int screenWidth )
	{
	  	setLengths( screenHeight );
		hPos = screenWidth;
	}
	
	//movePipes Method - Decrease (move left) hPos value by 1 every call
	public void movePipes()
	{
		hPos--;
	}
	
	//setLengths Method - Randomise pipe lengths without overlap and at least a gap of 100
	public void setLengths( int screenHeight )
	{
		Random gen = new Random();
		topLength = 10 + gen.nextInt( screenHeight );
		try
		{
			bottomLength = 10 + gen.nextInt( screenHeight - ( topLength + 100 ) );
		}
		catch( Exception e )
		{
			setLengths( screenHeight );
		}

		if( topLength >= screenHeight || bottomLength >= screenHeight )
			setLengths( screenHeight );
	}
	
	//getLengths Method - Return formatted String representation of pipe lengths
	public String getLengths()
	{
		return "Top: " + getTopLength() + " | Bottom: " + getBottomLength();
	}
	
	//getTopLength Method - Return int representation of top pipe length
	public int getTopLength()
	{
		return topLength; 
	}
	
	//getBottomLength Method - Return int representation of bottom pipe length
	public int getBottomLength()
	{
		return bottomLength; 
	}

	//setHPos Method - Set hPos to given value
	public void setHPos( int x )
	{
		hPos = x;
	}
	
	//getHPos Method - Return the hPos value
	public int getHPos()
	{
		return hPos; 
	}
}
