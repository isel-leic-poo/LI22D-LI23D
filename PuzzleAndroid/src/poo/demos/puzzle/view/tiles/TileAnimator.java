package poo.demos.puzzle.view.tiles;

import android.os.Handler;

/**
 * Class used to perform tile animations.
 */
class TileAnimator {
	
	public interface Callback {
		
		public void onCompleted();
	}

	/**
	 * Holds a reference to the animation queue
	 */
	private Handler animationQueue;

	/**
	 * Initiates the instance.
	 */
	public TileAnimator()
	{
		animationQueue = new Handler();
	}
	
	/**
	 * 
	 * @param listener
	 */
	public void doMove(final Callback listener)
	{
		final Runnable step = new Runnable() {
			private int count = 0;
			
			public void run()
			{
				if(++count == 5)
				{
					System.out.println("Done!");
					listener.onCompleted();
				}
				else {
					System.out.println(".");
					animationQueue.postDelayed(this, 1000);
				}
			}
		};
		animationQueue.postDelayed(step, 1000);
	}
}