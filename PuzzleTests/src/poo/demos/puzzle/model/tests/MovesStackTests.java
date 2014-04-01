package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import poo.demos.puzzle.model.Move;
import poo.demos.puzzle.model.Move.Delta;
import poo.demos.puzzle.model.MovesStack;
import poo.demos.puzzle.model.Piece;

public class MovesStackTests {

	// TODO: Elaborate when MovesStack is redone 
	
	@Test
	public void testCorrectInitiation() 
	{
		MovesStack moves = new MovesStack();
		assertTrue(moves.isEmpty());
	}
	
	@Test
	public void testSinglePushAndSinglePop()
	{
		MovesStack moves = new MovesStack();
		assertTrue(moves.isEmpty());
		Move someMove = new Move(Delta.DOWN, new Piece(0, 0));
		moves.push(someMove);
		assertFalse(moves.isEmpty());
		Move removedMove = moves.pop();
		assertSame(someMove, removedMove);
		assertTrue(moves.isEmpty());
	}
	
	@Test
	public void testFILODiscipline()
	{
		MovesStack moves = new MovesStack();
		Piece piece = new Piece(0, 0);
		// Adding moves to the stack
		Move firstMove = new Move(Delta.DOWN, piece);
		moves.push(firstMove);
		Move secondMove = new Move(Delta.RIGHT, piece);
		moves.push(secondMove);
		Move thirdMove = new Move(Delta.DOWN, piece);
		moves.push(thirdMove);
		// Check for correctness of current stack state
		assertEquals(3, moves.getSize());
		assertSame(thirdMove, moves.top());
		// Pop elements while checking FILO discipline
		assertSame(thirdMove, moves.pop());
		assertSame(secondMove, moves.pop());
		assertSame(firstMove, moves.pop());
	}
}
