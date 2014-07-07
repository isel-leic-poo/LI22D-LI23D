package poo.demos.puzzle;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Vibrator;
import poo.demos.common.views.Animator;
import poo.demos.common.views.Moveable;
import poo.demos.common.views.Tile;
import poo.demos.common.views.TileActionEvent;
import poo.demos.common.views.TileView;
import poo.demos.puzzle.BatchedAnimationQueue.PendingAnimation;
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
	
	private static boolean VIBRATE = true;
	
	private static final long VIBRATION_DURATION = 20;

	/**
	 * Constant string used as a key for storing and retrieving the view's state
	 */
	private static final String VIEW_STATE_KEY = "poo.demos.puzzle.model.Puzzle";
	
	/**
	 * Holds the current puzzle view
	 */
	private final TileView view;
	
	/**
	 * Holds the current puzzle model
	 */
	private final Puzzle model;
	
	/**
	 * Holds the animation queue
	 */
	private BatchedAnimationQueue animationQueue;
	
	/**
	 * Holds the associated activity
	 */
	private final Activity activity;
	
	/**
	 * Holds a reference to the system's vibrator service
	 */
	private Vibrator vibrator;
	
	/**
	 * Helper method that promotes a device vibration, if available
	 */
	private void vibrate()
	{
		if(vibrator != null)
			vibrator.vibrate(VIBRATION_DURATION);
	}

	/**
	 * Helper method that initiates the vibration functionality
	 */
	private void initVibrationFunction() 
	{
		if(VIBRATE)
		{
			vibrator = (Vibrator) activity.getSystemService(Activity.VIBRATOR_SERVICE);
			if(vibrator != null && !vibrator.hasVibrator())
				vibrator = null;
		}
	}
	
	/**
	 * Helper method that initiates the animation functionality
	 */
	private void initAnimation() 
	{
		final int STEP_COUNT = 20;
		animationQueue = new BatchedAnimationQueue(new Animator(STEP_COUNT, view), new BatchedAnimationQueue.OnAnimationCompletedListener() {
			@Override
			public void onCompleted(PendingAnimation animation) 
			{
				// Lets update the view. The last origin position is where the empty space will be placed
				// Updating the view accordingly
				Tile emptyTile = view.getTileAt(animation.targetTilesDestination[0].X, animation.targetTilesDestination[0].Y);
				assert !(emptyTile instanceof Moveable);
				
				for(int idx = 0; idx < animation.moves.length; ++idx)
				{
					Position destination = animation.targetTilesDestination[idx];
					view.setTileAt((Tile)animation.moves[idx].subject, destination.X, destination.Y);
				}

				Position emptyPosition = animation.targetTilesOrigin[animation.moves.length-1];
				view.setTileAt(emptyTile, emptyPosition.X, emptyPosition.Y);
				RectF emptyBounds = animation.moves[animation.moves.length-1].initialBounds;
				emptyTile.setPosition(emptyBounds.left, emptyBounds.top);
			}
		});
	}

	/**
	 * Helper method that initiates the controller's behavior.
	 */
	private void initBehavior() 
	{
		view.setOnTileActionListener(new TileView.OnTileActionListener() {
			
			private boolean isSwipeAlignedWithEmptySpace(Position emptySpace, TileActionEvent evt)
			{
				// Ugly as hell! I'll deal with it later
				if(emptySpace.X == evt.column)
					return (evt.swipeDirection == TileActionEvent.Direction.UP) 
							? emptySpace.Y < evt.row 
							: evt.swipeDirection == TileActionEvent.Direction.DOWN && emptySpace.Y > evt.row;

				if(emptySpace.Y == evt.row)
					return (evt.swipeDirection == TileActionEvent.Direction.LEFT) 
							? emptySpace.X < evt.column 
							: evt.swipeDirection == TileActionEvent.Direction.RIGHT && emptySpace.X > evt.column;
				
				return false;
			}
			
			@Override
			public void onTileTap(final TileActionEvent evt) 
			{
				if(!(evt.source instanceof Moveable))
					return;

				final Position origin = Position.fromCoordinates(evt.column, evt.row);
				final Piece targetPiece = model.getPieceAtPosition(origin);
				if(targetPiece == null || !model.doMove(targetPiece))
					return;

				// Move has been performed. Let's schedule the animation 
				// (enqueue animation, if another one is currently in progress)
				final Position destination = targetPiece.getPosition();
				Animator.Move move = new Animator.Move(
						(Moveable) evt.source, 
						view.getBoundsForTileAt(origin.X, origin.Y),
						view.getBoundsForTileAt(destination.X, destination.Y)
				);
				
				vibrate();
				animationQueue.enqueueAnimatedMove(new BatchedAnimationQueue.PendingAnimation(move, origin, destination));
			}

			@Override
			public void onTileSwipe(TileActionEvent evt) 
			{
				if(!(evt.source instanceof Moveable))
					return;
				
				// Check if the open space is in the direction of the swipe
				Position empty = model.getEmptySpacePosition();
				if(!isSwipeAlignedWithEmptySpace(empty, evt))
					return;
				
				// I must start at the piece that is adjacent to the empty space... 
				List<Piece> movingPieces = new LinkedList<Piece>();
				int currentColumn = evt.column, currentRow = evt.row;
				Piece targetPiece = null;
				while((targetPiece = model.getPieceAtPosition(Position.fromCoordinates(currentColumn, currentRow))) != null)
				{
					movingPieces.add(targetPiece);
					// Adjust coordinates and continue. Again, ugly as hell...
					switch(evt.swipeDirection)
					{
						case UP: currentRow -= 1; break;
						case DOWN: currentRow += 1; break;
						case LEFT: currentColumn -= 1; break;
						case RIGHT: currentColumn += 1; break;
					}
				}
				
				// Perform move and prepare animation
				BatchedAnimationQueue.PendingAnimationBuilder builder = new BatchedAnimationQueue.PendingAnimationBuilder();
				ListIterator<Piece> itr = movingPieces.listIterator(movingPieces.size());
				while(itr.hasPrevious())
				{
					targetPiece = itr.previous();
					final Position origin = targetPiece.getPosition();
					if(model.doMove(targetPiece))
					{
						// Move has been performed. Register it for animation
						final Position destination = targetPiece.getPosition();
						Animator.Move move = new Animator.Move(
								(Moveable) view.getTileAt(origin.X, origin.Y), 
								view.getBoundsForTileAt(origin.X, origin.Y),
								view.getBoundsForTileAt(destination.X, destination.Y)
						);
						builder.add(move, origin, destination);
					}
				}

				vibrate();
				// Let's schedule the animation 
				animationQueue.enqueueAnimatedMove(builder.build());
			}
		});
	}

	/**
	 * Initiates the controller with the given view and model instances
	 * 
	 * @param puzzleView The puzzle view instance
	 * @param puzzleModel The puzzle model instance
	 */
	private PuzzleController(TileView puzzleView, Puzzle puzzleModel, Activity owner)
	{
		view = puzzleView;
		model = puzzleModel;
		activity = owner;

		final NumberPuzzleTileFactory tileFactory = new NumberPuzzleTileFactory();
		tileFactory.setPuzzle(model);
		view.setTileProvider(tileFactory);
		
		initVibrationFunction();
		initAnimation();
		initBehavior();
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
	public static PuzzleController createController(TileView puzzleView, boolean shuffledPuzzle, Activity owner)
	{
		return new PuzzleController(
				puzzleView, 
				new Puzzle(puzzleView.getNumberOfTilesPerSide(), shuffledPuzzle),
				owner
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
	public static PuzzleController createController(TileView puzzleView, Bundle modelState, Activity owner)
	{
		PuzzleSurrogate modelSurrogate = (PuzzleSurrogate) modelState.getParcelable(VIEW_STATE_KEY);
		return new PuzzleController(puzzleView, modelSurrogate.getPuzzle(), owner);
	}
}
