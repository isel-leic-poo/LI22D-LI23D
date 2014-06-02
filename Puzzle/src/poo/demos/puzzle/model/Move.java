package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle moves.
 */
public class Move {
	
	/**
	 * Enumeration of valid displacements of puzzle pieces.
	 * Instances of this class are immutable.
	 */
	public static enum Delta {

		/**
		 * The existing enumeration instances. Notice that the order of declaration
		 * is relevant: it is used to compute the reverse delta.
		 */
		STEP_DOWN(0,1), STEP_UP(0,-1), STEP_RIGHT(1,0), STEP_LEFT(-1,0); 
		
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
		 * 
		 * @param dX The horizontal delta
		 * @param dY The vertical delta
		 */
		private Delta(int dX, int dY)
		{
			X = dX;
			Y = dY;
		}
		
		/**
		 * Gets this instance's reverse delta, that is, UP.getReverse() produces DOWN,
		 * LEFT.getReverse() produces RIGHT, and vice-versa. 
		 * 
		 * @return The reverse delta of the this instance.
		 */
		public Delta getReverse()
		{
			Delta[] values = values();
			// A bit of arithmetic incantation which is compromised with the 
			// order of the declaration of the enum's instances.
			int reverseIndex = (Math.abs(X) * 2) + (X + Y + 1) / 2;
			return values[reverseIndex];
		}
		
		/**
		 * Gets the instance that represents the given coordinates' variation, 
		 * or null if none exists.
		 * 
		 * Note that the implementation does not ensure that a {@code null} value 
		 * is always returned for invalid coordinates (a best effort approach).
		 * It is the responsibility of the call site to ensure that the coordinates 
		 * are within the represented range.
		 * 
		 * @param dX The horizontal variation
		 * @param dY The vertical variation
		 * @return The instance that represents the given variation 
		 */
		public static Delta getInstanceFromCoordinates(int dX, int dY)
		{
			if(Math.abs(dX + dY) != 1) return null;
			int index = Math.abs(dX) * 2 + Math.abs((dX + dY) - 1) / 2;
			return index < 0 || index >= values().length ? null : values()[index];
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
	 * @throws IllegalArgumentException if either argument is {@code null}
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
		return new Move(delta.getReverse(), this.target);
	}

	@Override
	public int hashCode() 
	{
		return this.toString().hashCode();
	}

	/**
	 * Checks if the instance is equivalent to the given one.
	 * Two move instances are equivalent if their delta is equivalent and 
	 * they refer to the same piece instance.  
	 * 
	 * @param other the other instance to use in the equivalence check
	 * @return {@code true} if the current instance is equivalent to the given one,
	 * {@code false} otherwise 
	 */
	@Override
	public boolean equals(Object other) 
	{
		if(!(other instanceof Move))
			return false;
		
		Move otherPiece = (Move) other;
		if(this == other)
			return true;
		
		return this.delta.equals(otherPiece.delta) && this.target == otherPiece.target;
	}

	@Override
	public String toString() 
	{
		return "Move " + target.toString() + " " + this.delta.toString();
	}
}
