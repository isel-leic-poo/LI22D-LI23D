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
public class PieceTests {

	@Test
	public void constructor_validArguments_noExceptionThrownAndStateCorrect() 
	{
		final int initialX = 2, initialY = 3; 
		Piece piece = new Piece(initialX, initialY);
		
		assertThat(piece.getX(), is(both(equalTo(initialX)).and(equalTo(piece.getInitialX()))));
		assertThat(piece.getY(), is(both(equalTo(initialY)).and(equalTo(piece.getInitialY()))));
		assertThat(piece.isAtCorrectPosition(), is(true));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeHorizontalCoordinateInConstructorArgument_exceptionThrown()
	{
		new Piece(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeVerticalCoordinateInConstructorArgument_exceptionThrown()
	{
		new Piece(2, -3);
	}

	@Test
	public void constructor_validPieceReferenceArgument_noExceptionThrownAndStateCorrect() 
	{
		
		Piece original = new Piece(0,0);
		original.setPosition(1, 1);
		Piece copy = new Piece(original);

		assertThat(copy, is(equalTo(original)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_nullPieceReferenceArgument_exceptionThrown()
	{
		new Piece(null);
	}
	
	@Test
	public void setPosition_withNonNegativeCoordinates_noExceptionThrownAndStateIsCorrect()
	{
		Piece piece = new Piece(0, 2);
		final int finalX = 1, finalY = 1;
		piece.setPosition(finalX, finalY);
		
		assertThat(piece.getX(), is(both(equalTo(finalX)).and(not(equalTo(piece.getInitialX())))));
		assertThat(piece.getY(), is(both(equalTo(finalY)).and(not(equalTo(piece.getInitialY())))));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_withNegativeHorizontalCoordinate_exceptionThrown()
	{
		new Piece(0, 1).setPosition(-1, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setPosition_withNegativeVerticalCoordinate_exceptionThrown()
	{
		new Piece(0, 1).setPosition(1, -1);
	}
	
	@Test
	public void move_movePieceToValidPositions_noExceptionThrownAndStateIsCorrect()
	{
		final int initialX = 0, initialY = 1;
		Piece piece = new Piece(initialX, initialY);

		final int firstDeltaX = 1, firstDeltaY = 1;
		piece.move(firstDeltaX, firstDeltaY);
		
		int expectedX = initialX + firstDeltaX; 
		int expectedY = initialY + firstDeltaY; 

		assertThat(piece.getX(), is(both(equalTo(expectedX)).and(not(equalTo(piece.getInitialX())))));
		assertThat(piece.getY(), is(both(equalTo(expectedY)).and(not(equalTo(piece.getInitialY())))));

		final int secondDeltaX = 0, secondDeltaY = -1;
		piece.move(secondDeltaX, secondDeltaY);
		
		expectedX += secondDeltaX;
		expectedY += secondDeltaY;
		
		assertThat(piece.getX(), is(both(equalTo(expectedX)).and(not(equalTo(piece.getInitialX())))));
		assertThat(piece.getY(), is(both(equalTo(expectedY)).and(equalTo(piece.getInitialY()))));

		final int thirdDeltaX = -1, thirdDeltaY = 0;
		piece.move(thirdDeltaX, thirdDeltaY);
		
		expectedX += thirdDeltaX;
		expectedY += thirdDeltaY;
		
		assertThat(piece.getX(), is(both(equalTo(expectedX)).and(equalTo(piece.getInitialX()))));
		assertThat(piece.getY(), is(both(equalTo(expectedY)).and(equalTo(piece.getInitialY()))));
	}
	
	@Test(expected = IllegalStateException.class)
	public void move_movePieceToAnInvalidPosition_exceptionThrown()
	{
		new Piece(0, 1).move(-1, 1);
	}
	
	@Test
	public void isAtCorrectPosition_pieceAtInitialPosition_returnsTrue()
	{
		Piece piece = new Piece(1,2);
		assertThat(piece.isAtCorrectPosition(), is(true));
	}

	@Test
	public void isAtCorrectPosition_pieceNotAtInitialPosition_returnsFalse()
	{
		Piece piece = new Piece(1,2);
		piece.move(1, 0);
		assertThat(piece.isAtCorrectPosition(), is(false));
	}

	@Test
	public void isAtCorrectPosition_pieceReturnsToInitialPosition_returnsTrue()
	{
		Piece piece = new Piece(1,2);
		piece.move(1, 0);
		piece.move(-1, 0);
		assertThat(piece.isAtCorrectPosition(), is(true));
	}

	
	@Test
	public void equals_equivalentPieces_returnsTrue()
	{
		Piece one = new Piece(2, 1);
		Piece other = new Piece(2, 1);
		assertThat(one, is(equalTo(other)));
		assertThat(other, is(equalTo(one)));
	}
	
	@Test
	public void hashCode_equivalentPieces_returnsSameValue()
	{
		Piece one = new Piece(2, 1);
		Piece other = new Piece(2, 1);
		assertThat(one.hashCode(), is(equalTo(other.hashCode())));
	}
	
	@Test
	public void equals_nullArgument_returnsFalse()
	{
		Piece piece = new Piece(2, 1);
		assertThat(piece, is(not(equalTo(null))));
	}
	
	@Test
	public void equals_samePiece_returnsTrue()
	{
		Piece piece = new Piece(2, 1);
		assertThat(piece, is(equalTo(piece)));
	}
	
	@Test
	public void equals_differentPieces_returnsFalse()
	{
		Piece one = new Piece(1, 1);
		Piece other = new Piece(2, 1);

		assertThat(one, is(not(equalTo(other))));
		assertThat(other, is(not(equalTo(one))));
	}
}
