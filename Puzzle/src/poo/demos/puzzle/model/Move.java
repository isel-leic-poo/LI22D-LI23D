package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle moves.
 */
public class Move {
	
	/**
	 * Enumeration of valid displacements of puzzle pieces.
	 * Instances of this class are immutable.
	 */
	public static class Delta {

		/**
		 * The horizontal delta
		 */
		public final int X;
		
		/**
		 * The vertical delta
		 */
		public final int Y;
		
		/**
		 * Initiates an instance with the given arguments.
		 * @param dX The horizontal delta
		 * @param dY The vertical delta
		 */
		private Delta(int dX, int dY)
		{
			X = dX;
			Y = dY;
		}
		
		/**
		 * Static fields that hold the valid displacements for 
		 * puzzle pieces.
		 */
		public static final Delta UP, DOWN, LEFT, RIGHT;
		
		static 
		{
		 	UP = new Delta(0, -1);
		 	DOWN = new Delta(0, 1);
		 	LEFT = new Delta(-1, 0);
		 	RIGHT = new Delta(1, 0);
		}
	}

	/**
	 * The move's coordinates variation.
	 */
	public final Delta delta;
	
	/**
	 * The piece to which the move is to be applied.
	 */
	public final Piece target;
	
	/**
	 * Initiates an instance with the given arguments.
	 * 
	 * @param delta The variation to apply to the piece 
	 * @param piece The piece to which the move is to be applied
	 */
	public Move(Delta delta, Piece piece)
	{
		if(delta == null || piece == null)
			throw new IllegalArgumentException();
		
		this.delta = delta;
		this.target = piece;
	}
	
	/**
	 * Creates and returns the inverse move (i.e. relative to the 
	 * current move instance).
	 * 
	 * @return The inverse move 
	 */
	public Move getReverseMove()
	{
		Delta reverseDelta = new Delta(this.delta.X * -1, this.delta.Y * -1);
		return new Move(reverseDelta, this.target);
	}
}
