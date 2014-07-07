package poo.demos.puzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import poo.demos.common.views.Animator;
import poo.demos.common.views.Animator.Move;
import poo.demos.puzzle.model.Position;

/**
 * Class that represents a batched animation queue. This class is specifically 
 * designed to be used with {@link PuzzleController}. Although there are no source 
 * code dependencies, this class was not designed with generality in mind.  
 * 
 * Animations are performed in batches: animations in the same batch are performed
 * simultaneously; animation batches are performed sequentially.   
 */
public class BatchedAnimationQueue {

	/**
	 * Interface that specifies the contract to receive notifications 
	 * regarding animation completion. 
	 */
	public static interface OnAnimationCompletedListener {

		/**
		 * Signals that the animation has been completed.
		 * 
		 * @param animation the animation that has been completed
		 */
		public void onCompleted(PendingAnimation animation);
	}

	/**
	 * Class whose instances are responsible for building instances of {@link PendingAnimation} 
	 */
	public static class PendingAnimationBuilder
	{
		private final List<Move> moves = new LinkedList<Move>();
		private final List<Position> origins = new LinkedList<Position>();
		private final List<Position> destinations = new LinkedList<Position>();

		/**
		 * Adds the given move to the pending animation currently being built.
		 * 
		 * @param move the move to be added
		 * @param origin the move's origin
		 * @param destination the move's destination
		 */
		public void add(Move move, Position origin, Position destination)
		{
			moves.add(move);
			origins.add(origin);
			destinations.add(destination);
		}
		
		/**
		 * Builds a {@link PendingAnimation} instance with the moves accumulated thus far 
		 *  
		 * @return the built instance
		 */
		public PendingAnimation build()
		{
			return new PendingAnimation(
					moves.toArray(new Move[moves.size()]), 
					origins.toArray(new Position[moves.size()]), 
					destinations.toArray(new Position[moves.size()])
			);
		}
	}
	
	
	/**
	 * Class whose instances represent pending animations
	 */
	public static class PendingAnimation
	{
		/**
		 * The moves that will be subject to animation
		 */
		public final Move[] moves;
		
		/**
		 * The target tiles original positions
		 */
		public final Position[] targetTilesOrigin;
		
		/**
		 * The target tiles final positions
		 */
		public final Position[] targetTilesDestination;
		
		/**
		 * Initiates an instance with the given arguments.
		 * 
		 * @param move the move that will be subject to animation
		 * @param targetTileOrigin the target tile original position
		 * @param targetTileDestination the target tile final position
		 */
		public PendingAnimation(Move move, Position targetTileOrigin, Position targetTileDestination)
		{
			this(new Move[] { move }, new Position[] { targetTileOrigin }, new Position[] { targetTileDestination });
		}
		
		/**
		 * Initiates an instance with the given arguments.
		 * 
		 * @param moves the moves that will be subject to animation
		 * @param targetTilesOrigin the target tiles' original positions
		 * @param targetTilesDestination the target tiles' final positions
		 */
		public PendingAnimation(Move[] moves, Position[] targetTilesOrigin, Position[] targetTilesDestination)
		{
			this.moves = moves;
			this.targetTilesOrigin = targetTilesOrigin;
			this.targetTilesDestination = targetTilesDestination;
		}
	}

	/**
	 * Holds the instance used to perform animations
	 */
	private final Animator animator;
	
	/**
	 * Holds the animation queue, that is, the queue of pending animations.
	 */
	private final Queue<PendingAnimation> animationQueue;

	/**
	 * The registered listener for receiving animation completion notifications 
	 */
	private final OnAnimationCompletedListener listener; 
	
	/**
	 * Helper method that triggers the next animation (the one at the queue's head) 
	 */
	private void triggerAnimation(final PendingAnimation animation)
	{
		// An animation is not in progress. Let's trigger it
		animator.submitMoves(Arrays.asList(animation.moves), new Animator.OnAnimationCompletedListener() {
			@Override
			public void onCompleted(List<Move> moves) 
			{
				// Remove completed animation from the queue of pending animations
				animationQueue.remove();

				if(listener != null)
					listener.onCompleted(animation);
				
				// Are there any pending animations? If so, let's trigger the next one
				if(!animationQueue.isEmpty())
					triggerAnimation(animationQueue.peek());
			} 
		});
	}

	/**
	 * Initiates an instance with the given arguments
	 * 
	 * @param animator the animation object
	 * @param listener the listener that will receive animation completion notifications
	 */
	public BatchedAnimationQueue(Animator animator, OnAnimationCompletedListener listener)
	{
		this.listener = listener;
		this.animator = animator;
		this.animationQueue = new LinkedList<PendingAnimation>();
	}
	
	/**
	 * Adds the given animated move to the animation queue.
	 * 
	 * @param animatedMove the animated move to be appended to the animation queue 
	 */
	public void enqueueAnimatedMove(PendingAnimation animatedMove)
	{
		animationQueue.add(animatedMove);
		
		// If an animation is not yet in progress, we must trigger it. Otherwise, we enqueue it.
		if(animationQueue.size() == 1)
			triggerAnimation(animationQueue.peek());
	}
}
