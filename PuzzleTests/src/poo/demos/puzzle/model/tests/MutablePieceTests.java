package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

import poo.demos.puzzle.model.*;

/**
 * Note to students:
 * Just for fun, my test suits are using Hamcrest's matchers.
 * Notice the increased expressiveness.. ;)
 * 
 * For your own tests I recommend you use (for now) JUnit's assertions (e.g. assertSame,
 * assertEqual, assertTrue, and so on).
 * 
 * The next example illustrates the use of JUnit's assertions to express the 
 * instantiation_validConstructorArguments_noExceptionThrownAndStateCorrect unit test.
 * <pre> 
 * {@code
 * 		assertEquals(2, piece.getX());
 * 		assertEquals(2, piece.getInitialX());
 * 		assertEquals(3, piece.getY());
 * 		assertEquals(3, piece.getInitialY());
 * 		assertTrue(piece.isAtCorrectPosition());
 * }
 * </pre>
 */
public class MutablePieceTests {
	
	@Test
	public void constructor_validArguments_noExceptionThrownAndStateCorrect() 
	{
		final Position initial = Position.fromCoordinates(2, 3); 
		Piece piece = new MutablePiece(initial);
		
		assertThat(piece.getPosition(), is(both(equalTo(initial)).and(equalTo(piece.getInitialPosition()))));
		assertThat(piece.isAtCorrectPosition(), is(true));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeHorizontalCoordinateInConstructorArgument_exceptionThrown()
	{
		new MutablePiece(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeVerticalCoordinateInConstructorArgument_exceptionThrown()
	{
		new MutablePiece(2, -3);
	}

	@Test
	public void setPosition_withNonNegativeCoordinates_noExceptionThrownAndStateIsCorrect()
	{
		MutablePiece piece = new MutablePiece(0, 2);
		final Position destination = Position.fromCoordinates(1, 1); 
		piece.moveTo(destination);
		
		assertThat(piece.getPosition(), is(both(equalTo(destination)).and(not(equalTo(piece.getInitialPosition())))));
	}
	
	@Test
	public void move_movePieceToValidPositions_noExceptionThrownAndStateIsCorrect()
	{
		final Position initial = Position.fromCoordinates(0, 1);
		final MutablePiece piece = new MutablePiece(initial);

		final Move.Delta firstDelta = Move.Delta.STEP_RIGHT; 
		piece.moveBy(firstDelta);
		Position expected = Position.fromCoordinates(initial.X + firstDelta.X, initial.Y + firstDelta.Y);
		assertThat(piece.getPosition(), is(both(equalTo(expected)).and(not(equalTo(piece.getInitialPosition())))));

		final Move.Delta secondDelta = Move.Delta.STEP_UP;
		piece.moveBy(secondDelta);
		expected = Position.fromCoordinates(expected.X + secondDelta.X, expected.Y + secondDelta.Y);
		assertThat(piece.getPosition(), is(both(equalTo(expected)).and(not(equalTo(piece.getInitialPosition())))));

		final Move.Delta thirdDelta = Move.Delta.STEP_LEFT;
		piece.moveBy(thirdDelta);
		expected = Position.fromCoordinates(expected.X + thirdDelta.X, expected.Y + thirdDelta.Y);
		assertThat(piece.getPosition(), is(both(equalTo(expected)).and(not(equalTo(piece.getInitialPosition())))));

		final Move.Delta lastDelta = Move.Delta.STEP_DOWN;
		piece.moveBy(lastDelta);
		expected = Position.fromCoordinates(expected.X + lastDelta.X, expected.Y + lastDelta.Y);
		assertThat(piece.getPosition(), is(both(equalTo(expected)).and(equalTo(piece.getInitialPosition()))));
	}
	
	@Test(expected = IllegalStateException.class)
	public void move_movePieceToAnInvalidPosition_exceptionThrown()
	{
		new MutablePiece(0, 1).moveBy(Move.Delta.STEP_LEFT);
	}
	
	@Test
	public void isAtCorrectPosition_pieceAtInitialPosition_returnsTrue()
	{
		Piece piece = new MutablePiece(1,2);
		assertThat(piece.isAtCorrectPosition(), is(true));
	}

	@Test
	public void isAtCorrectPosition_pieceNotAtInitialPosition_returnsFalse()
	{
		MutablePiece piece = new MutablePiece(1,2);
		piece.moveBy(Move.Delta.STEP_RIGHT);
		assertThat(piece.isAtCorrectPosition(), is(false));
	}

	@Test
	public void isAtCorrectPosition_pieceReturnsToInitialPosition_returnsTrue()
	{
		MutablePiece piece = new MutablePiece(1,2);
		piece.moveBy(Move.Delta.STEP_RIGHT);
		piece.moveBy(Move.Delta.STEP_LEFT);
		assertThat(piece.isAtCorrectPosition(), is(true));
	}

	
	@Test
	public void equals_equivalentPieces_returnsTrue()
	{
		Piece one = new MutablePiece(2, 1);
		Piece other = new MutablePiece(2, 1);
		assertThat(one, is(equalTo(other)));
		assertThat(other, is(equalTo(one)));
	}
	
	@Test
	public void hashCode_equivalentPieces_returnsSameValue()
	{
		Piece one = new MutablePiece(2, 1);
		Piece other = new MutablePiece(2, 1);
		assertThat(one.hashCode(), is(equalTo(other.hashCode())));
	}
	
	@Test
	public void equals_nullArgument_returnsFalse()
	{
		Piece piece = new MutablePiece(2, 1);
		assertThat(piece, is(notNullValue()));
	}
	
	@Test
	public void equals_samePiece_returnsTrue()
	{
		Piece piece = new MutablePiece(2, 1);
		assertThat(piece, is(equalTo(piece)));
	}
	
	@Test
	public void equals_differentPieces_returnsFalse()
	{
		Piece one = new MutablePiece(1, 1);
		Piece other = new MutablePiece(2, 1);

		assertThat(one, is(not(equalTo(other))));
		assertThat(other, is(not(equalTo(one))));
	}
}
