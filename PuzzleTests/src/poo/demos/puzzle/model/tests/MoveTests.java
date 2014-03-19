package poo.demos.puzzle.model.tests;

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
	public void testIncorrectInitiation() 
	{
		new Move(Move.Delta.UP, null);
	}

}
