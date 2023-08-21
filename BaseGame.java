import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class BaseGame extends Application
{
	public static void main( String[] args )
	{
		launch( args );
	}

	@Override
	public void start( Stage stage )
	{
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		int screenWidth = ( int ) screen.getWidth();
		int screenHeight = ( int ) screen.getHeight() - 50;
		BorderPane root = new BorderPane();
		Scene scene = new Scene( root, screenWidth, screenHeight );
		stage.setScene( scene );
		stage.setTitle( "FlappyBird" );

		Image startImg = new Image( "ButtonUp_Play.png" );
		ImageView startIV = new ImageView( startImg );
		Button startBt = new Button( "", startIV );
		root.setCenter( startBt );
		
		startBt.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle( ActionEvent event )
			{
				scene.setRoot( play( screenWidth, screenHeight ) );
			}
		} );

		//GUI
		scene.setRoot( root );
		stage.show();
	}

	//play Method
	public static BorderPane play( int screenWidth, int screenHeight )
	{
		BorderPane root = new BorderPane();

		//Bird
		Bird bird = new Bird( screenHeight );
		Rectangle birdRect = new Rectangle( screenWidth / 3, bird.getHeight(), 20, 20 );
		Timer timer = new Timer();
	  	timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			bird.gravity();
	    		}
	  	}, 0, 50 ); //1000 = 1 second

	  	timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			birdRect.setY( bird.getHeight() );
	    		}
	  	}, 0, 50 ); //1000 = 1 second

		//Up Button
		Button upBt = new Button( "UP" );
		upBt.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle( ActionEvent event )
			{
				bird.up();
				birdRect.setY( bird.getHeight() );
			}
		} );

		//Pipe1
		Pipes pipe1 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe1Rect = new Rectangle( screenWidth, 0, 30, pipe1.getTopLength() );
		Rectangle bPipe1Rect = new Rectangle( screenWidth, screenHeight - pipe1.getBottomLength(), 30, pipe1.getBottomLength() );
		tPipe1Rect.setFill( Color.BLUE );
		bPipe1Rect.setFill( Color.BLUE );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe1.movePipes();
				tPipe1Rect.setX( pipe1.getHPos() );
				bPipe1Rect.setX( pipe1.getHPos() );

				//Pipe1 hitbox
				if( ( pipe1.getHPos() <= birdRect.getX() + 19 && pipe1.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe1Rect.getY() + pipe1.getTopLength() || birdRect.getY() + 19 >= bPipe1Rect.getY() ) )
					timer.cancel();

				//Loop pipe1
				if( pipe1.getHPos() <= 0 )
				{
					pipe1.setHPos( screenWidth );
					pipe1.setLengths( screenHeight );
					tPipe1Rect.setHeight( pipe1.getTopLength() );
					bPipe1Rect.setHeight( pipe1.getBottomLength() );
					bPipe1Rect.setX( screenHeight - pipe1.getBottomLength() );
				}
	    		}
	  	}, 0, 20 ); //1000 = 1 second

		//Pipe2
		Pipes pipe2 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe2Rect = new Rectangle( screenWidth, 0, 30, pipe2.getTopLength() );
		Rectangle bPipe2Rect = new Rectangle( screenWidth, screenHeight - pipe2.getBottomLength(), 30, pipe2.getBottomLength() );
		tPipe2Rect.setFill( Color.RED );
		bPipe2Rect.setFill( Color.RED );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe2.movePipes();
				tPipe2Rect.setX( pipe2.getHPos() );
				bPipe2Rect.setX( pipe2.getHPos() );

				//Pipe2 hitbox
				if( ( pipe2.getHPos() <= birdRect.getX() + 19 && pipe2.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe2Rect.getY() + pipe2.getTopLength() || birdRect.getY() + 19 >= bPipe2Rect.getY() ) )
					timer.cancel();

				//Loop pipe2
				if( pipe2.getHPos() <= 0 )
				{
					pipe2.setHPos( screenWidth );
					pipe2.setLengths( screenHeight );
					tPipe2Rect.setHeight( pipe2.getTopLength() );
					bPipe2Rect.setHeight( pipe2.getBottomLength() );
					bPipe2Rect.setX( screenHeight - pipe2.getBottomLength() );
				}
	    		}
	  	}, 6000, 20 ); //1000 = 1 second

		//Pipe3
		Pipes pipe3 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe3Rect = new Rectangle( screenWidth, 0, 30, pipe3.getTopLength() );
		Rectangle bPipe3Rect = new Rectangle( screenWidth, screenHeight - pipe3.getBottomLength(), 30, pipe3.getBottomLength() );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe3.movePipes();
				tPipe3Rect.setX( pipe3.getHPos() );
				bPipe3Rect.setX( pipe3.getHPos() );

				//Pipe3 hitbox
				if( ( pipe3.getHPos() <= birdRect.getX() + 19 && pipe3.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe3Rect.getY() + pipe3.getTopLength() || birdRect.getY() + 19 >= bPipe3Rect.getY() ) )
					timer.cancel();

				//Loop pipe3
				if( pipe3.getHPos() <= 0 )
				{
					pipe3.setHPos( screenWidth );
					pipe3.setLengths( screenHeight );
					tPipe3Rect.setHeight( pipe3.getTopLength() );
					bPipe3Rect.setHeight( pipe3.getBottomLength() );
					bPipe3Rect.setX( screenHeight - pipe3.getBottomLength() );
				}
	    		}
	  	}, 12000, 20 ); //1000 = 1 second

		root.setBottom( upBt );
		root.getChildren().addAll( birdRect, tPipe1Rect, bPipe1Rect, tPipe2Rect, bPipe2Rect, tPipe3Rect, bPipe3Rect );

		return root;
	}
}
