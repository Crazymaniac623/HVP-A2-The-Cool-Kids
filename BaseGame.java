import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.geometry.*;
import java.util.*;

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

		Image startImg = new Image( "Images/ButtonUp_Play.png" );
		ImageView startIV = new ImageView( startImg );
		Button startBt = new Button( "", startIV );
		
		//Bird design
		ToggleGroup birdRBtGroup = new ToggleGroup();

		//Blue
		RadioButton blueRBt = new RadioButton();
		blueRBt.setToggleGroup( birdRBtGroup );
		blueRBt.setSelected( true ); //Default bird option
		blueRBt.setGraphic( new ImageView( new Image( "Images/BirdDown_Blue.png", 100, 100, true, true ) ) );

		//Duck
		RadioButton duckRBt = new RadioButton();
		duckRBt.setToggleGroup( birdRBtGroup );
		duckRBt.setGraphic( new ImageView( new Image( "Images/BirdDown_Duck.png", 100, 100, true, true ) ) );

		//Grey
		RadioButton greyRBt = new RadioButton();
		greyRBt.setToggleGroup( birdRBtGroup );
		greyRBt.setGraphic( new ImageView( new Image( "Images/BirdDown_Grey.png", 100, 100, true, true ) ) );

		//Lovebird
		RadioButton lovebirdRBt = new RadioButton();
		lovebirdRBt.setToggleGroup( birdRBtGroup );
		lovebirdRBt.setGraphic( new ImageView( new Image( "Images/BirdDown_Lovebird.png", 100, 100, true, true ) ) );

		//Spots
		RadioButton spotsRBt = new RadioButton();
		spotsRBt.setToggleGroup( birdRBtGroup );
		spotsRBt.setGraphic( new ImageView( new Image( "Images/BirdDown_Spots.png", 100, 100, true, true ) ) );

		VBox birds = new VBox( 10 );
		birds.getChildren().addAll( blueRBt, duckRBt, greyRBt, lovebirdRBt, spotsRBt );
		birds.setAlignment( Pos.CENTER_LEFT );
		root.setCenter( startBt );
		root.setLeft( birds );

		//Start game
		startBt.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle( ActionEvent event )
			{
				//Determine selected bird design
				String birdDesign = "";
				if( blueRBt.isSelected() )
					birdDesign = "Blue";
				else if( duckRBt.isSelected() )
					birdDesign = "Duck";
				else if( greyRBt.isSelected() )
					birdDesign = "Grey";
				else if( lovebirdRBt.isSelected() )
					birdDesign = "Lovebird";
				else if( spotsRBt.isSelected() )
					birdDesign = "Spots";

				scene.setRoot( play( screenWidth, screenHeight, birdDesign ) );
			}
		} );		

		//GUI
		scene.setRoot( root );
		stage.show();
	}

	//play Method
	public static BorderPane play( int screenWidth, int screenHeight, String birdDesign )
	{
		BorderPane root = new BorderPane();

		//Bird
		Bird bird = new Bird( screenHeight );
		Rectangle birdRect = new Rectangle( screenWidth / 3, bird.getHeight(), 50, 50 );
		Image birdDownImg = new Image( "Images/BirdDown_" + birdDesign + ".png" );
		Image birdUpImg = new Image( "Images/BirdUp_" + birdDesign + ".png" );
		birdRect.setFill( new ImagePattern( birdDownImg ) );

		Timer timer = new Timer();
	  	timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			bird.gravity();
				birdRect.setFill( new ImagePattern( birdDownImg ) );
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
				birdRect.setFill( new ImagePattern( birdUpImg ) );
				birdRect.setY( bird.getHeight() );
			}
		} );

		//Pipe graphic
		Image lowerPipeImg = new Image( "Images/BarUpper_Blue.png" );
		Image upperPipeImg = new Image( "Images/BarLower_Blue.png" );

		//Pipe1
		Pipes pipe1 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe1Rect = new Rectangle( screenWidth, 0, 30, pipe1.getTopLength() );
		Rectangle bPipe1Rect = new Rectangle( screenWidth, screenHeight - pipe1.getBottomLength(), 30, pipe1.getBottomLength() );
		tPipe1Rect.setFill( new ImagePattern( upperPipeImg ) );
		bPipe1Rect.setFill( new ImagePattern( lowerPipeImg ) );

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
		tPipe2Rect.setFill( new ImagePattern( upperPipeImg ) );
		bPipe2Rect.setFill( new ImagePattern( lowerPipeImg ) );

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
		tPipe3Rect.setFill( new ImagePattern( upperPipeImg ) );
		bPipe3Rect.setFill( new ImagePattern( lowerPipeImg ) );

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
