package poo.demos.puzzle.model.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import poo.demos.puzzle.model.Grid;
import poo.demos.puzzle.model.Piece;

/**
 * Note to students:
 * Just for fun, my test suits are using Hamcrest's matchers.
 * Notice the increased expressiveness.. ;)
 * 
 * For your own tests I recommend you use (for now) JUnit's assertions (e.g. assertSame,
 * assertEqual, assertTrue, and so on).
 */
public class GridTests {

	private static final int SIDE = 4;
	private static Grid puzzle;

	@BeforeClass
	public static void preparePuzzleInstance()
	{
		puzzle = Grid.createRandomPuzzle(SIDE);
	}
	
	@Test
	public void createrandomPuzzle_withSizeEqualToSIDE_returnsValidGridWithShuffledPieces() 
	{
		assertThat(puzzle, is(not(nullValue())));
		
		int pieceCount = 0;
		for(int y = 0; y < SIDE; ++y)
		{
			for(int x = 0; x < SIDE; ++x)
			{
				Piece piece = puzzle.getPieceAtPosition(x, y);
				if(piece != null) 
				{
					pieceCount += 1;
					assertThat(piece.getX(), is(equalTo(x)));
					assertThat(piece.getY(), is(equalTo(y)));
				}
			}
		}
		
		assertThat(pieceCount, is(equalTo(SIDE*SIDE - 1)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getPieceAtPosition_withNegativeHorizontalCoordinate_exceptionThrown()
	{
		puzzle.getPieceAtPosition(-1, 0);
	}


	@Test(expected = IllegalArgumentException.class)
	public void getPieceAtPosition_withNegativeVerticalCoordinate_exceptionThrown()
	{
		puzzle.getPieceAtPosition(1, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getPieceAtPosition_withOutOfBoundsHorizontalCoordinate_exceptionThrown()
	{
		puzzle.getPieceAtPosition(SIDE, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getPieceAtPosition_withOutOfBoundsVerticalCoordinate_exceptionThrown()
	{
		puzzle.getPieceAtPosition(1, SIDE);
	}
	
	@Test
	public void getPieceAtPosition_withValidCoordinates_returnsPieceWithCorrectState()
	{
		int currentX = 0, currentY = 0;
		Piece piece = puzzle.getPieceAtPosition(currentX, currentY);
		// If we got the puzzle's hole, let's try another piece
		if(piece == null)
			piece = puzzle.getPieceAtPosition(currentX, currentY+=1);
		
		assertThat(piece, is(not(nullValue())));
		assertThat(piece.getX(), is(equalTo(currentX)));
		assertThat(piece.getY(), is(equalTo(currentY)));
	}
}
