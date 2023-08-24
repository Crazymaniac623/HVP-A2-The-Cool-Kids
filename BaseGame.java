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
		Image lowerPipeTubeImg = new Image( "Images/BarLower_Tube.png" );
		Image lowerPipeEndImg = new Image( "Images/BarLower_End.png" );
		Image upperPipeTubeImg = new Image( "Images/BarUpper_Tube.png" );
		Image upperPipeEndImg = new Image( "Images/BarUpper_End.png" );

		//Pipe1
		Pipes pipe1 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe1Rect = new Rectangle( screenWidth, 0, 30, pipe1.getTopLength() - 10 );
		Rectangle bPipe1Rect = new Rectangle( screenWidth, screenHeight - ( pipe1.getBottomLength() - 10 ), 30, pipe1.getBottomLength() - 10 );
		Rectangle tPipe1EndRect = new Rectangle( screenWidth, pipe1.getTopLength() - 10, 30, 10 );
		Rectangle bPipe1EndRect = new Rectangle( screenWidth, screenHeight - pipe1.getBottomLength(), 30, 10 );
		tPipe1Rect.setFill( new ImagePattern( upperPipeTubeImg ) );
		bPipe1Rect.setFill( new ImagePattern( lowerPipeTubeImg ) );
		tPipe1EndRect.setFill( new ImagePattern( upperPipeEndImg ) );
		bPipe1EndRect.setFill( new ImagePattern( lowerPipeEndImg ) );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe1.movePipes();
				tPipe1Rect.setX( pipe1.getHPos() );
				tPipe1EndRect.setX( pipe1.getHPos() );
				bPipe1Rect.setX( pipe1.getHPos() );
				bPipe1EndRect.setX( pipe1.getHPos() );

				//Pipe1 hitbox
				if( ( pipe1.getHPos() <= birdRect.getX() + 49 && pipe1.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe1Rect.getY() + pipe1.getTopLength() + 10 || birdRect.getY() + 49 >= bPipe1Rect.getY() - 10 ) )
					timer.cancel();

				//Height limits
				if( birdRect.getY() <= 0 || birdRect.getY() + 49 >= screenHeight )
					timer.cancel();

				//Loop pipe1
				if( pipe1.getHPos() <= 0 )
				{
					pipe1.setHPos( screenWidth );
					pipe1.setLengths( screenHeight );

					tPipe1Rect.setHeight( pipe1.getTopLength() - 10 );
					tPipe1EndRect.setX( tPipe1Rect.getX() );
					tPipe1EndRect.setY( tPipe1Rect.getHeight() );

					bPipe1Rect.setHeight( pipe1.getBottomLength() + 10 );
					bPipe1Rect.setY( screenHeight - ( pipe1.getBottomLength() + 10 ) );
					bPipe1EndRect.setX( bPipe1Rect.getX() );
					bPipe1EndRect.setY( bPipe1Rect.getY() - 10 );
				}
	    		}
	  	}, 0, 20 ); //1000 = 1 second

		//Pipe2
		Pipes pipe2 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe2Rect = new Rectangle( screenWidth, 0, 30, pipe2.getTopLength() - 10 );
		Rectangle bPipe2Rect = new Rectangle( screenWidth, screenHeight - ( pipe2.getBottomLength() - 10 ), 30, pipe2.getBottomLength() - 10 );
		Rectangle tPipe2EndRect = new Rectangle( screenWidth, pipe2.getTopLength() - 10, 30, 10 );
		Rectangle bPipe2EndRect = new Rectangle( screenWidth, screenHeight - pipe2.getBottomLength(), 30, 10 );
		tPipe2Rect.setFill( new ImagePattern( upperPipeTubeImg ) );
		bPipe2Rect.setFill( new ImagePattern( lowerPipeTubeImg ) );
		tPipe2EndRect.setFill( new ImagePattern( upperPipeEndImg ) );
		bPipe2EndRect.setFill( new ImagePattern( lowerPipeEndImg ) );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe2.movePipes();
				tPipe2Rect.setX( pipe2.getHPos() );
				tPipe2EndRect.setX( pipe2.getHPos() );
				bPipe2Rect.setX( pipe2.getHPos() );
				bPipe2EndRect.setX( pipe2.getHPos() );

				//Pipe2 hitbox
				if( ( pipe2.getHPos() <= birdRect.getX() + 49 && pipe2.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe2Rect.getY() + pipe2.getTopLength() + 10 || birdRect.getY() + 49 >= bPipe2Rect.getY() - 10 ) )
					timer.cancel();

				//Height limits
				if( birdRect.getY() <= 0 || birdRect.getY() + 49 >= screenHeight )
					timer.cancel();

				//Loop pipe2
				if( pipe2.getHPos() <= 0 )
				{
					pipe2.setHPos( screenWidth );
					pipe2.setLengths( screenHeight );

					tPipe2Rect.setHeight( pipe2.getTopLength() - 10);
					tPipe2EndRect.setX( tPipe2Rect.getX() );
					tPipe2EndRect.setY( tPipe2Rect.getHeight() );

					bPipe2Rect.setHeight( pipe2.getBottomLength() + 10 );
					bPipe2Rect.setY( screenHeight - ( pipe2.getBottomLength() + 10 ) );
					bPipe2EndRect.setX( bPipe2Rect.getX() );
					bPipe2EndRect.setY( bPipe2Rect.getY() - 10 );
				}
	    		}
	  	}, 6000, 20 ); //1000 = 1 second

		//Pipe3
		Pipes pipe3 = new Pipes( screenHeight, screenWidth );
		Rectangle tPipe3Rect = new Rectangle( screenWidth, 0, 30, pipe3.getTopLength() - 10 );
		Rectangle bPipe3Rect = new Rectangle( screenWidth, screenHeight - ( pipe3.getBottomLength() - 10 ), 30, pipe3.getBottomLength() - 10 );
		Rectangle tPipe3EndRect = new Rectangle( screenWidth, pipe3.getTopLength() - 10, 30, 10 );
		Rectangle bPipe3EndRect = new Rectangle( screenWidth, screenHeight - pipe3.getBottomLength(), 30, 10 );
		tPipe3Rect.setFill( new ImagePattern( upperPipeTubeImg ) );
		bPipe3Rect.setFill( new ImagePattern( lowerPipeTubeImg ) );
		tPipe3EndRect.setFill( new ImagePattern( upperPipeEndImg ) );
		bPipe3EndRect.setFill( new ImagePattern( lowerPipeEndImg ) );

		timer.schedule( new TimerTask() 
	  	{
	  		@Override
	    		public void run()
	    		{
	      			pipe3.movePipes();
				tPipe3Rect.setX( pipe3.getHPos() );
				tPipe3EndRect.setX( pipe3.getHPos() );
				bPipe3Rect.setX( pipe3.getHPos() );
				bPipe3EndRect.setX( pipe3.getHPos() );

				//Pipe3 hitbox
				if( ( pipe3.getHPos() <= birdRect.getX() + 49 && pipe3.getHPos() + 29 >= birdRect.getX() ) && ( birdRect.getY() <= tPipe3Rect.getY() + pipe3.getTopLength() + 10 || birdRect.getY() + 49 >= bPipe3Rect.getY() - 10 ) )
					timer.cancel();

				//Height limits
				if( birdRect.getY() <= 0 || birdRect.getY() + 49 >= screenHeight )
					timer.cancel();

				//Loop pipe3
				if( pipe3.getHPos() <= 0 )
				{
					pipe3.setHPos( screenWidth );
					pipe3.setLengths( screenHeight );

					tPipe3Rect.setHeight( pipe3.getTopLength() + 10 );
					tPipe3EndRect.setX( tPipe3Rect.getX() );
					tPipe3EndRect.setY( tPipe3Rect.getHeight() );

					bPipe3Rect.setHeight( pipe3.getBottomLength() - 10 );
					bPipe3Rect.setY( screenHeight - ( pipe3.getBottomLength() + 10 ) );
					bPipe3EndRect.setX( bPipe3Rect.getX() );
					bPipe3EndRect.setY( bPipe3Rect.getY() - 10 );
				}
	    		}
	  	}, 12000, 20 ); //1000 = 1 second

		root.setBottom( upBt );
		root.getChildren().addAll( birdRect, tPipe1Rect, tPipe1EndRect, bPipe1Rect, bPipe1EndRect, tPipe2Rect, tPipe2EndRect, bPipe2Rect, bPipe2EndRect, tPipe3Rect, tPipe3EndRect, bPipe3Rect, bPipe3EndRect );

		return root;
	}
}
