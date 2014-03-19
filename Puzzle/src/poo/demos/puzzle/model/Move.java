package poo.demos.puzzle.model;

public class Move {
	
	public static class Delta {

		public final int X;
		public final int Y;
		
		private Delta(int dX, int dY)
		{
			X = dX;
			Y = dY;
		}
		
		public static final Delta UP, DOWN, LEFT, RIGHT;
		
		static 
		{
		 	UP = new Delta(0, -1);
		 	DOWN = new Delta(0, 1);
		 	LEFT = new Delta(-1, 0);
		 	RIGHT = new Delta(1, 0);
		}
	}

	public final Delta delta;
	public final Piece target;
	
	public Move(Delta delta, Piece piece)
	{
		if(piece == null)
			throw new IllegalArgumentException();
		
		this.delta = delta;
		this.target = piece;
	}
}
