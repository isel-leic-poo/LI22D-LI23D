package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import poo.demos.puzzle.model.Move;
import poo.demos.puzzle.model.Piece;

public class MoveTests {

	@Test
	public void testCorrectInitiation() 
	{
		new Move(Move.Delta.UP, new Piece(0, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectPieceInitiation() 
	{
		new Move(Move.Delta.UP, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIncorrectDeltaInitiation() 
	{
		new Move(null, new Piece(0, 0));
	}

	@Test
	public void testReverseMoveComputation() 
	{
		Piece targetPiece = new Piece(0, 0);
		Move move = new Move(Move.Delta.UP, targetPiece);
		Move reverseMove = move.getReverseMove();
		assertSame(targetPiece, reverseMove.target);
		// TODO: Must ensure equivalence implementation 
		assertEquals(Move.Delta.DOWN, reverseMove.delta);
	}
}
