import java.util.Timer;
import java.util.TimerTask;

public class Bird
{
	private int height;

	//Bird screenHeight Constructor
	public Bird( int screenHeight )
	{
	   	height = screenHeight / 2;
	}
	
	//gravity Method - Raise height value by 1 every half second (Visually goes down)
	public void gravity()
	{
	  	height ++;
	}
	
	//up Method - Decrease height value by 15 (visually goes up)
	public void up()
	{
	  	height -= 15;
	}
	
	//getHeight Method - Return value of height
	public int getHeight()
	{
	  	return height; 
	}
}