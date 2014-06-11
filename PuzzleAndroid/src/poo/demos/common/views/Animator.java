package poo.demos.common.views;

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
		 * @param subject The {@link poo.demos.common.views.Moveable} instance
		 * that was subject to animation
		 */
		public void onCompleted(Moveable subject);
		
		/**
		 * Signals that an animation step has been performed.
		 * 
		 * @param subject The {@link poo.demos.common.views.Moveable} instance
		 * that was subject to animation
		 */
		public void onStepPerformed(Moveable subject);
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
			
			public void run()
			{
				if(--remainingSteps == 0)
				{
					subject.moveTo(finalBounds.left, finalBounds.top);
					if(listener!= null)
						listener.onCompleted(subject);
				}
				else
				{
					subject.moveBy(stepDeltaX, stepDeltaY);
					if(listener!= null)
						listener.onStepPerformed(subject);
					animationQueue.post(this);
				}
			}
		});
	}
}