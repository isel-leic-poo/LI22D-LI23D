package poo.demos.puzzle.view;

import android.graphics.RectF;
import android.os.Handler;

/**
 * Class used to perform tile animations.
 */
public class Animator {
	
	/**
	 * Interface that specifies the contract to receive notifications 
	 * regarding animation progress. 
	 */
	public interface OnAnimationListener {

		/**
		 * Signals that the animation has been completed.
		 * 
		 * @param subject The {@link poo.demos.puzzle.view.Moveable} instance
		 * that was subject to animation
		 * @param affectedArea The area affected by the animation.
		 */
		public void onCompleted(Moveable subject, RectF affectedArea);
		
		/**
		 * Signals that an animation step has been performed.
		 * 
		 * @param subject The {@link poo.demos.puzzle.view.Moveable} instance
		 * that was subject to animation
		 * @param affectedArea The area affected by the animation.
		 */
		public void onStepPerformed(Moveable subject, RectF affectedArea);
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
	 * Initiate the instance with the given animation steps.
	 *  
	 * @param numberOfSteps The number of steps to be used in each animation.
	 */
	public Animator(int numberOfSteps)
	{
		animationQueue = new Handler();
		stepCount = numberOfSteps;
	}

	/**
	 * Submits an animation to be performed on the given subject.
	 * 
	 * @param subject The object to be animated
	 * @param finalBounds The object's destination 
	 * @param listener The instance that will be notified of the animation progress,
	 * or {@code null} if the calling site is not interested in receiving such notifications.
	 */
	public void submitMove(final Moveable subject, final RectF finalBounds, final OnAnimationListener listener)
	{
		// Compute step displacement
		final float stepDeltaX = (finalBounds.centerX() - subject.getCurrentBounds().centerX()) / stepCount;
		final float stepDeltaY = (finalBounds.centerY() - subject.getCurrentBounds().centerY()) / stepCount;
		
		animationQueue.post(new Runnable() {
			private int remainingSteps = stepCount;
			// Copying the subject's initial bounds
			private final RectF affectedArea = new RectF(subject.getCurrentBounds());
			
			public void run()
			{
				if(--remainingSteps == 0)
				{
					subject.moveTo(finalBounds.left, finalBounds.top);
					affectedArea.union(subject.getCurrentBounds());
					if(listener!= null)
						listener.onCompleted(subject, affectedArea);
				}
				else
				{
					subject.moveBy(stepDeltaX, stepDeltaY);
					affectedArea.union(subject.getCurrentBounds());
					if(listener!= null)
						listener.onStepPerformed(subject, affectedArea);
					animationQueue.post(this);
				}
			}
		});
	}
}