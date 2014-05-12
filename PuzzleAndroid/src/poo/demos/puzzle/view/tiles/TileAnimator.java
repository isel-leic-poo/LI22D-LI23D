package poo.demos.puzzle.view.tiles;

import android.graphics.RectF;
import android.os.Handler;

/**
 * Class used to perform tile animations.
 */
class TileAnimator {
	
	/**
	 * Interface that specifies the contract to receive notifications 
	 * regarding animation progress. 
	 */
	public interface Callback {
		
		/**
		 * Signals that the animation has been completed.
		 */
		public void onCompleted();
		
		/**
		 * 
		 * @param affectedArea
		 */
		public void onStep(RectF affectedArea);
	}

	/**
	 * Holds a reference to the animation queue
	 */
	private final Handler animationQueue;
	
	/**
	 * The animation's duration, expressed in the number of required steps, which
	 * is computed according to the chosen frame rate.
	 */
	private final int stepCount;

	/**
	 * Initiates the instance.
	 */
	public TileAnimator(int numberOfSteps)
	{
		animationQueue = new Handler();
		stepCount = numberOfSteps;
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void doMove(final Tile tile, final RectF finalBounds, final Callback listener)
	{
		// Compute step displacement
		final float stepDeltaX = (finalBounds.centerX() - tile.getBounds().centerX()) / stepCount;
		final float stepDeltaY = (finalBounds.centerY() - tile.getBounds().centerY()) / stepCount;
		
		animationQueue.post(new Runnable() {
			private int remainingSteps = stepCount;
			public void run()
			{
				if(--remainingSteps == 0)
				{
					tile.moveTo(finalBounds.left, finalBounds.top);
					listener.onCompleted();
				}
				else
				{
					tile.moveBy(stepDeltaX, stepDeltaY);
					listener.onStep(null);
					animationQueue.post(this);
				}
			}
		});
	}
}