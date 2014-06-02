package poo.demos.puzzle.model.tests;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import poo.demos.puzzle.model.Grid;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;

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
	public void createPuzzle_withSizeEqualToSIDE_returnsValidGridWithUnshuffledPieces()
	{
		Grid nonShuffledPuzzle = Grid.createPuzzle(SIDE);
		assertThat(nonShuffledPuzzle, is(not(nullValue())));
		
		int pieceCount = 0;
		for(int x = 0; x < SIDE; ++x)
		{
			for(int y = 0; y < SIDE; ++y)
			{
				Piece piece = nonShuffledPuzzle.getPieceAtPosition(x, y);
				if(piece != null) 
				{
					assertThat(piece.getPosition().X, is(equalTo(x)));
					assertThat(piece.getPosition().Y, is(equalTo(y)));
					assertThat(piece.isAtCorrectPosition(), is(true));
					pieceCount += 1;
				}
				else {
					assertThat(x , is(equalTo(SIDE-1)));
					assertThat(y , is(equalTo(SIDE-1)));
				}
			}
		}
		
		assertThat(pieceCount, is(equalTo(SIDE*SIDE - 1)));
	}
	
	@Test
	public void createRandomPuzzle_withSizeEqualToSIDE_returnsValidGridWithShuffledPieces() 
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
					assertThat(piece.getPosition().X, is(equalTo(x)));
					assertThat(piece.getPosition().Y, is(equalTo(y)));
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
		assertThat(piece.getPosition().X, is(equalTo(currentX)));
		assertThat(piece.getPosition().Y, is(equalTo(currentY)));
	}
	
	@Test
	public void doMoveToEmptySpace_withPieceAdjacentToEmptySpace_returnsTrueAndGridStateIsCorrect()
	{
		// The adjacent piece selection assumes that the empty space is at the grid's 
		// lower-right corner 
		Position emptySpace = puzzle.getEmptySpacePosition();
		Piece pieceToMove = puzzle.getPieceAtPosition(emptySpace.X-1, emptySpace.Y);
		Position initialPosition = pieceToMove.getPosition();
		
		assertThat(puzzle.doMove(pieceToMove), is(true));
		assertThat(puzzle.getEmptySpacePosition(), is(equalTo(initialPosition)));
		assertThat(puzzle.getPieceAtPosition(pieceToMove.getPosition()), is(equalTo(pieceToMove)));
	}

	@Test
	public void doMoveToEmptySpace_withPieceNotAdjacentToEmptySpace_returnsFalseAndGridStateIsCorrect()
	{
		// The piece selection assumes that the empty space is at the grid's 
		// lower-right corner 
		Position initialEmptyPosition = puzzle.getEmptySpacePosition();
		Piece pieceToMove = puzzle.getPieceAtPosition(initialEmptyPosition.X-2, initialEmptyPosition.Y);
		Position initialPosition = pieceToMove.getPosition();
		
		assertThat(puzzle.doMove(pieceToMove), is(false));
		assertThat(puzzle.getEmptySpacePosition(), is(equalTo(initialEmptyPosition)));
		assertThat(pieceToMove.getPosition(), is(equalTo(initialPosition)));
		assertThat(puzzle.getPieceAtPosition(pieceToMove.getPosition()), is(equalTo(pieceToMove)));
	}
}
