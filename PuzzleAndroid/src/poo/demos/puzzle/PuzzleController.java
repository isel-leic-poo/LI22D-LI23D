package poo.demos.puzzle;

import android.os.Bundle;
import poo.demos.common.views.Animator;
import poo.demos.common.views.Moveable;
import poo.demos.common.views.Tile;
import poo.demos.common.views.TileActionEvent;
import poo.demos.common.views.TileView;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;
import poo.demos.puzzle.model.Puzzle;
import poo.demos.puzzle.views.NumberPuzzleTileFactory;
import poo.demos.puzzle.viewstate.PuzzleSurrogate;

/**
 * Class that implements the puzzle controller according to 
 * the Model-View-Controller (MVC) design pattern.
 */
public class PuzzleController {

	private static final String VIEW_STATE_KEY = "poo.demos.puzzle.model.Puzzle";
	
	/**
	 * Holds the current puzzle view
	 */
	private final TileView view;
	
	/**
	 * Holds the current puzzle model
	 */
	private Puzzle model;
	
	/**
	 * Holds a reference to the animation queue
	 */
	private final Animator animationQueue;
	
	/**
	 * Holds a reference to the view listener
	 */
	private final TileView.OnTileActionListener viewListener;
	
	/**
	 * Initiates the controller with the given view and model instances
	 * 
	 * @param puzzleView The puzzle view instance
	 * @param puzzleModel The puzzle model instance
	 */
	private PuzzleController(TileView puzzleView, Puzzle puzzleModel)
	{
		view = puzzleView;
		model = puzzleModel;
		
		final NumberPuzzleTileFactory tileFactory = new NumberPuzzleTileFactory();
		tileFactory.setPuzzle(model);
		view.setTileProvider(tileFactory);
		
		final int STEP_COUNT = 30;
		animationQueue = new Animator(STEP_COUNT);
		
		viewListener = new TileView.OnTileActionListener() {
			
			@Override
			public void onTileTouch(final TileActionEvent evt) 
			{
				if(!(evt.source instanceof Moveable))
					return;

				Piece targetPiece = model.getPieceAtPosition(Position.fromCoordinates(evt.column, evt.row));
				if(targetPiece == null || !model.doMove(targetPiece))
					return;

				// Move has been performed. Let's trigger the animation while inhibiting user interaction
				view.setOnTileActionListener(null);

				final Moveable subject = (Moveable) evt.source;
				final float subjectInitialLeft = subject.getCurrentBounds().left;
				final float subjectInitialTop = subject.getCurrentBounds().top;

				final Position emptySpacePosition = targetPiece.getPosition();
				final Tile emptyTile = view.getTileAt(emptySpacePosition.X, emptySpacePosition.Y);
				
				animationQueue.submitMove(subject, emptyTile.getBounds(), new Animator.OnAnimationListener() {

					@Override
					public void onCompleted(Moveable subject) 
					{
						// The view's empty space is still on the space occupied by the moved tile.
						// Updating the view accordingly
						view.setTileAt(evt.source, emptySpacePosition.X, emptySpacePosition.Y);
						view.setTileAt(emptyTile, evt.column, evt.row);
						emptyTile.setPosition(subjectInitialLeft, subjectInitialTop);
						view.setOnTileActionListener(viewListener);
					}

					// TODO: Consider interface segregation (ISP) to eliminate unnecessary calls
					@Override
					public void onStepPerformed(Moveable subject) { }
				});
			}
		};
		
		view.setOnTileActionListener(viewListener);
	}

	/**
	 * Saves the puzzle's state in the given bundle.
	 * 
	 * @param stateBundle The bundle used to store the puzzle's state
	 */
	public void saveState(Bundle stateBundle)
	{
		stateBundle.putParcelable(VIEW_STATE_KEY, new PuzzleSurrogate(model));
	}

	/**
	 * Factory method that creates a controller instance with the given view and a newly 
	 * instantiated model. 
	 * 
	 * @param puzzleView The view instance 
	 * @param shuffledPuzzle a boolean value indicating whether the newly instantiated model
	 * should be shuffled or not
	 * @return The controller instance
	 */
	public static PuzzleController createController(TileView puzzleView, boolean shuffledPuzzle)
	{
		return new PuzzleController(
				puzzleView, 
				new Puzzle(puzzleView.getNumberOfTilesPerSide(), shuffledPuzzle)
		);
	}
	
	/**
	 * Factory method that creates a controller instance with the given view and the model
	 * retrieved from the received {@link Bundle} instance.
	 * 
	 * @param puzzleView The view instance 
	 * @param modelState The model state
	 * @return The controller instance
	 */
	public static PuzzleController createController(TileView puzzleView, Bundle modelState)
	{
		PuzzleSurrogate modelSurrogate = (PuzzleSurrogate) modelState.getParcelable(VIEW_STATE_KEY);
		return new PuzzleController(puzzleView, modelSurrogate.getPuzzle());
	}
}
