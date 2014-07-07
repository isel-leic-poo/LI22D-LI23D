package poo.demos.common.views;

import java.util.Collections;
import java.util.List;

import poo.demos.utils.Lists;
import android.graphics.RectF;
import android.view.View;

/**
 * Class used to perform tile animations.
 */
public class Animator {
		
	/**
	 * Interface that specifies the contract to receive notifications 
	 * regarding animation completion. 
	 */
	public static interface OnAnimationCompletedListener {

		/**
		 * Signals that the animation has been completed.
		 * 
		 * @param moves the moves that were subject to animation
		 */
		public void onCompleted(List<Move> moves);
	}
	
	/**
	 * Class whose instances represent requests for animated moves.
	 */
	public static class Move
	{
		/**
		 * The object to be moved.
		 */
		public final Moveable subject;
		
		/**
		 * The origin of the object that will be moved.
		 */
		public final RectF initialBounds;
		
		/**
		 * The final destination of the object that will be moved.
		 */
		public final RectF finalBounds;
		
		/**
		 * Initiates an instance with the given arguments.
		 * 
		 * @param subject the object to be moved
		 * @param initialBounds the origin of the object that will be moved
		 * @param finalBounds the final destination of the object that will be moved
		 */
		public Move(Moveable subject, RectF initialBounds, RectF finalBounds)
		{
			this.subject = subject;
			this.initialBounds = initialBounds;
			this.finalBounds = finalBounds;
		}
	}
	
	/**
	 * Helper class whose instances represent animations.
	 */
	private class Animation implements Runnable
	{
		/**
		 * The number of animation steps still to be executed
		 */
		private int remainingSteps;
		
		/**
		 * The animation step deltas of each move to be animated.
		 */
		private float[] stepDeltasX, stepDeltasY;
		
		/**
		 * The list of moves
		 */
		private final List<Move> moves;
		
		/**
		 * The completion callback
		 */
		private final OnAnimationCompletedListener doneListener;
		
		/**
		 * Initiates an instance with the given list of moves.
		 * 
		 * @param moves the list of moves that comprise the animation
		 * @param doneListener the completion callback
		 */
		public Animation(List<Move> moves, OnAnimationCompletedListener doneListener)
		{
			this.moves = moves;
			this.doneListener = doneListener;
			remainingSteps = stepCount;
			
			stepDeltasX = new float[moves.size()];
			stepDeltasY = new float[moves.size()];
			
			Lists.forEach(moves, new Lists.Func<Move>() {
				@Override
				public void apply(Move move, int atIndex) 
				{
					stepDeltasX[atIndex] = (move.finalBounds.centerX() - move.subject.getCurrentBounds().centerX()) / stepCount;
					stepDeltasY[atIndex] = (move.finalBounds.centerY() - move.subject.getCurrentBounds().centerY()) / stepCount;
				}
			});
		}
		
		@Override
		public void run() 
		{
			if(--remainingSteps == 0)
			{
				Lists.forEach(moves, new Lists.Func<Move>() {
					@Override
					public void apply(Move move, int atIndex) 
					{
						move.subject.moveTo(move.finalBounds.left, move.finalBounds.top);
					}
				});
				
				if(doneListener != null)
					doneListener.onCompleted(moves);
			}
			else
			{
				Lists.forEach(moves, new Lists.Func<Move>() {
					@Override
					public void apply(Move move, int atIndex) 
					{
						move.subject.moveBy(stepDeltasX[atIndex], stepDeltasY[atIndex]);
					}
				});
				targetView.postOnAnimation(Animation.this);
				//animationQueue.post(Animation.this);
			}
		}
	}
	
	/**
	 * The animation's duration, expressed in the number of required steps, which
	 * is computed according to the chosen frame rate.
	 */
	private final int stepCount;
	
	/**
	 * Holds a reference to the view where the animation will be performed
	 */
	private final View targetView; 
	
	/**
	 * Initiate the instance with the given animation steps.
	 *  
	 * @param numberOfSteps The number of steps to be used in each animation.
	 */
	public Animator(int numberOfSteps, View targetView)
	{
		stepCount = numberOfSteps;
		this.targetView = targetView;
	}

	/**
	 * Submits an animation to be performed on the given subject. The animation is triggered immediately.
	 * 
	 * @param move the object describing the animation
	 * @param doneListener The listener for animation completed notifications, or {@code null} 
	 * if the calling site is not interested in receiving such notifications.
	 */
	public void submitMove(final Move move, final OnAnimationCompletedListener doneListener)
	{
		targetView.postOnAnimation(new Animation(Collections.singletonList(move), doneListener));
	}
	
	/**
	 * Submits a list of animations to be performed on the given subjects. The animations are triggered 
	 * immediately and performed simultaneously.
	 * 
	 * @param moves the list of objects describing the animation
	 * @param doneListener The listener for animation completed notifications, or {@code null} 
	 * if the calling site is not interested in receiving such notifications.
	 */
	public void submitMoves(final List<Move> moves, final OnAnimationCompletedListener doneListener)
	{
		targetView.postOnAnimation(new Animation(moves, doneListener));
	}
}