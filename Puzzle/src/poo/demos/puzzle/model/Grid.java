package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle grids.
 * For the sake of simplification, grids are always squares. 
 */
public class Grid {
	
	/**
	 * Holds the two-dimensional array that holds the puzzle's pieces. 
	 */
	private final MutablePiece[][] grid;
	
	/**
	 * The puzzle's size
	 */
	private int size;
	
	/**
	 * Initializes a grid instance with the given size.
	 *  
	 * @param size the size of the square
	 * @throws IllegalArgumentException if size is less or equal than {@code 0} 
	 */
	private Grid(int size)
	{
		this.size = size;
		grid = new MutablePiece[size][size];
	}
	
	/**
	 * Factory method that produces a shuffled puzzle. 
	 * 
	 * Implementation note: The current algorithm always leaves an empty space
	 * at the bottom rightmost position of the grid.
	 *  
	 * @param size the size of the puzzle's side. The size of the puzzle must be, at least,
	 * of two elements per side. 
	 * @return the shuffled instance
	 * @throws IllegalArgumentException if size is less or equal than {@code 1} 
	 */
	public static Grid createRandomPuzzle(int size)
	{
		if(size <= 1)
			throw new IllegalArgumentException();
		
		// Initialize placeholder
		MutablePiece[] pieces = new MutablePiece[size * size - 1];
		
		// Initialize pieces
		for(int idx = 0; idx < pieces.length; ++idx)
		{
			int initialX = idx % size;
			int initialY = idx / size;
			pieces[idx] = new MutablePiece(initialX, initialY);
		}

		// Initialize grid 
		Grid instance = new Grid(size);
		
		for(int idx = 0; idx < pieces.length; ++idx)
		{
			// Select a piece
			int selectedIdx = (int) (Math.random() * (pieces.length - idx));
			MutablePiece selectedPiece = pieces[selectedIdx];
			pieces[selectedIdx] = pieces[pieces.length - idx - 1];
			
			// Place it
			int currentX = idx % size;
			int currentY = idx / size;
			selectedPiece.setPosition(currentX, currentY);
			instance.grid[currentY][currentX] = selectedPiece;
		}
		
		return instance;
	}
	
	/**
	 * Gets the piece at the given position. If the position is free,
	 * the method returns {@code null}.
	 * 
	 * @param x the horizontal coordinate value (0 < x < puzzleSize)
	 * @param y the horizontal coordinate value (0 < y < puzzleSize)
	 * @return the piece at the given position, or {@code null} if that position 
	 * is empty
	 * @throws IllegalArgumentException if the parameters are not within the 
	 * grid's bounds (0 <= value < gridSide) 
	 */
	public Piece getPieceAtPosition(int x, int y)
	{
		if(x < 0 || x >= size || y < 0 || y >= size)
			throw new IllegalArgumentException();
		
		return grid[y][x]; 
	}

	/**
	 * Gets the grid's size.
	 * 
	 * @return the grid's size
	 */
	public int getSize()
	{
		return grid.length;
	}
	
	// TODO: Add methods to move pieces
	
	public void doMove(Move move)
	{
		MutablePiece targetPiece = grid[move.target.getY()][move.target.getX()];
		
		// TODO: validate move
		
		targetPiece.move(move.delta.X, move.delta.Y);
		grid[targetPiece.getY()][targetPiece.getX()] = targetPiece;
		
	}
}
