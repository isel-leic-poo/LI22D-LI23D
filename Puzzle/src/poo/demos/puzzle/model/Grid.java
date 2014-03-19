package poo.demos.puzzle.model;

/**
 * Class whose instances represent puzzle grids.
 * For the sake of simplification, grids are always squares. 
 */
public class Grid {
	
	/**
	 * Holds the bi-dimensional array that holds the puzzle's pieces. 
	 */
	private final Piece[][] grid;
	
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
		if(size <= 0)
			throw new IllegalArgumentException();

		this.size = size;
		grid = new Piece[size][size];
	}
	
	/**
	 * Factory method that produces a shuffled puzzle. 
	 * 
	 * Implementation note: The current algorithm always leaves an empty space
	 * at the bottom rightmost position of the grid.
	 *  
	 * @param side the size of the puzzle's side
	 * @return the shuffled instance
	 * @throws IllegalArgumentException if size is less or equal than {@code 0} 
	 */
	public static Grid createRandomPuzzle(int side)
	{
		// Initialize placeholder
		Piece[] pieces = new Piece[side * side - 1];
		
		// Initialize pieces
		for(int idx = 0; idx < pieces.length; ++idx)
		{
			int initialX = idx % side;
			int initialY = idx / side;
			pieces[idx] = new Piece(initialX, initialY);
		}

		// Initialize grid 
		Grid instance = new Grid(side);
		
		for(int idx = 0; idx < pieces.length; ++idx)
		{
			// Select a piece
			int selectedIdx = (int) (Math.random() * (pieces.length - idx));
			Piece selectedPiece = pieces[selectedIdx];
			pieces[selectedIdx] = pieces[pieces.length - idx - 1];
			
			// Place it
			int currentX = idx % side;
			int currentY = idx / side;
			selectedPiece.setPosition(currentX, currentY);
			instance.grid[currentY][currentX] = selectedPiece;
		}
		
		return instance;
	}
	
	/**
	 * Gets the piece at the given position. If the position is free,
	 * the method returns {@code null}
	 * 
	 * @param x the horizontal coordinate value (0 < x < puzzleSize)
	 * @param y the horizontal coordinate value (0 < y < puzzleSize)
	 * @return the piece at the given position, or {@code null} if that position 
	 * is empty
	 */
	public Piece getPieceAtPosition(int x, int y)
	{
		if(x < 0 || x >= size || y < 0 || y >= size)
			throw new IllegalArgumentException();
		
		return grid[y][x];
	}
}
