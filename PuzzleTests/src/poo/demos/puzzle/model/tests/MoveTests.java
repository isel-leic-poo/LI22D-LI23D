package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import poo.demos.puzzle.model.Move;
import poo.demos.puzzle.model.Move.Delta;
import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.tests.mocks.MockPiece;

/**
 * Note to students:
 * Just for fun, my test suits are using Hamcrest's matchers.
 * Notice the increased expressiveness.. ;)
 * 
 * For your own tests I recommend you use (for now) JUnit's assertions (e.g. assertSame,
 * assertEqual, assertTrue, and so on).
 * 
 * The next example illustrates the use of JUnit's assertions to express the 
 * deltaGetReverse_correctReverseComputation_returnsReverse unit test.
 * <pre> 
 * {@code
 * 		assertSame(Delta.DOWN, Delta.UP.getReverse());
 * 		assertSame(Delta.UP, Delta.DOWN.getReverse());
 * 		assertSame(Delta.LEFT, Delta.RIGHT.getReverse());
 * 		assertSame(Delta.RIGHT, Delta.LEFT.getReverse());
 * }
 * </pre>
 */
public class MoveTests {
	
	@Test
	public void deltaGetReverse_correctReverseComputation_returnsReverse()
	{
		assertThat(Delta.STEP_UP.getReverse(), is(sameInstance(Delta.STEP_DOWN)));
		assertThat(Delta.STEP_DOWN.getReverse(), is(sameInstance(Delta.STEP_UP)));
		assertThat(Delta.STEP_RIGHT.getReverse(), is(sameInstance(Delta.STEP_LEFT)));
		assertThat(Delta.STEP_LEFT.getReverse(), is(sameInstance(Delta.STEP_RIGHT)));
	}
	
	@Test
	public void instantiation_validConstructorArguments_noExceptionThrownAndFieldsAreCorrect() 
	{
		Piece piece = new MockPiece(0, 0);
		Move move = new Move(Move.Delta.STEP_UP, piece);
		assertThat(move.delta, is(sameInstance(Move.Delta.STEP_UP)));
		assertThat(move.target, is(sameInstance(piece)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiation_nullPieceInConstructorArgument_exceptionThrown() 
	{
		new Move(Move.Delta.STEP_UP, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void instantiation_nullDeltaInConstructorArgument_exceptionThrown() 
	{
		new Move(null, new MockPiece(0, 0));
	}

	@Test
	public void equals_equivalentMoves_returnsTrue()
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move one = new Move(Move.Delta.STEP_UP, targetPiece);
		Move other = new Move(Move.Delta.STEP_UP, targetPiece);
		assertThat(one, is(equalTo(other)));
		assertThat(other, is(equalTo(one)));
	}
	
	@Test
	public void hashCode_equivalentMoves_returnsSameValue()
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move one = new Move(Move.Delta.STEP_UP, targetPiece);
		Move other = new Move(Move.Delta.STEP_UP, targetPiece);
		assertThat(one.hashCode(), is(equalTo(other.hashCode())));
	}
	
	@Test
	public void equals_nullArgument_returnsFalse()
	{
		Move move = new Move(Move.Delta.STEP_UP, new MockPiece(0, 0));
		assertThat(move, is(not(equalTo(null))));
	}
	
	@Test
	public void equals_sameMove_returnsTrue()
	{
		Move move = new Move(Move.Delta.STEP_UP, new MockPiece(0, 0));
		assertThat(move, is(equalTo(move)));
	}
	
	@Test
	public void equals_differentMoves_returnsFalse()
	{
		Move one = new Move(Move.Delta.STEP_DOWN, new MockPiece(0, 0));
		Move other = new Move(Move.Delta.STEP_UP, new MockPiece(1, 0));
		assertThat(one, is(not(equalTo(other))));
		assertThat(other, is(not(equalTo(one))));
	}

	@Test
	public void getReverseMove_reverseUp_returnsDown() 
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move move = new Move(Move.Delta.STEP_UP, targetPiece);
		Move expectedReverseMove = new Move(Move.Delta.STEP_DOWN, targetPiece);
		assertThat(move.getReverseMove(), is(equalTo(expectedReverseMove)));
	}

	@Test
	public void getReverseMove_reverseDown_returnsUp() 
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move move = new Move(Move.Delta.STEP_DOWN, targetPiece);
		Move expectedReverseMove = new Move(Move.Delta.STEP_UP, targetPiece);
		assertThat(move.getReverseMove(), is(equalTo(expectedReverseMove)));
	}

	@Test
	public void getReverseMove_reverseLeft_returnsRight() 
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move move = new Move(Move.Delta.STEP_LEFT, targetPiece);
		Move expectedReverseMove = new Move(Move.Delta.STEP_RIGHT, targetPiece);
		assertThat(move.getReverseMove(), is(equalTo(expectedReverseMove)));
	}

	@Test
	public void getReverseMove_reverseRight_returnsLeft() 
	{
		Piece targetPiece = new MockPiece(0, 0);
		Move move = new Move(Move.Delta.STEP_RIGHT, targetPiece);
		Move expectedReverseMove = new Move(Move.Delta.STEP_LEFT, targetPiece);
		assertThat(move.getReverseMove(), is(equalTo(expectedReverseMove)));
	}
	
	@Test
	public void getInstanceFromCoordinates_withAllDirectionsCoordinates_returnsCorrectInstances()
	{
		assertThat(Move.Delta.getInstanceFromCoordinates(0, 1), is(sameInstance(Move.Delta.STEP_DOWN)));
		assertThat(Move.Delta.getInstanceFromCoordinates(0, -1), is(sameInstance(Move.Delta.STEP_UP)));
		assertThat(Move.Delta.getInstanceFromCoordinates(1, 0), is(sameInstance(Move.Delta.STEP_RIGHT)));
		assertThat(Move.Delta.getInstanceFromCoordinates(-1, 0), is(sameInstance(Move.Delta.STEP_LEFT)));
	}
	
	@Test
	public void getInstanceFromCoordinates_withInvalidDirectionsCoordinates_returnsNull()
	{
		assertThat(Move.Delta.getInstanceFromCoordinates(0, 0), is(nullValue()));
		assertThat(Move.Delta.getInstanceFromCoordinates(1, 1), is(nullValue()));
		assertThat(Move.Delta.getInstanceFromCoordinates(1, -1), is(nullValue()));
		assertThat(Move.Delta.getInstanceFromCoordinates(-1, -1), is(nullValue()));
		assertThat(Move.Delta.getInstanceFromCoordinates(-1, 1), is(nullValue()));
	}
}
