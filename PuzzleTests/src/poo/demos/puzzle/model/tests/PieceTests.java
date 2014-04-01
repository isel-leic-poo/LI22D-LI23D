package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

import poo.demos.puzzle.model.*;
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
		Piece piece = new MockPiece(initialX, initialY);
		
		assertThat(piece.getX(), is(both(equalTo(initialX)).and(equalTo(piece.getInitialX()))));
		assertThat(piece.getY(), is(both(equalTo(initialY)).and(equalTo(piece.getInitialY()))));
		assertThat(piece.isAtCorrectPosition(), is(true));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeHorizontalCoordinateInConstructorArgument_exceptionThrown()
	{
		new MockPiece(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void constructor_negativeVerticalCoordinateInConstructorArgument_exceptionThrown()
	{
		new MockPiece(2, -3);
	}

	@Test
	public void equals_equivalentPieces_returnsTrue()
	{
		Piece one = new MockPiece(2, 1);
		Piece other = new MockPiece(2, 1);
		assertThat(one, is(equalTo(other)));
		assertThat(other, is(equalTo(one)));
	}
	
	@Test
	public void hashCode_equivalentPieces_returnsSameValue()
	{
		Piece one = new MockPiece(2, 1);
		Piece other = new MockPiece(2, 1);
		assertThat(one.hashCode(), is(equalTo(other.hashCode())));
	}
	
	@Test
	public void equals_nullArgument_returnsFalse()
	{
		Piece piece = new MockPiece(2, 1);
		assertThat(piece, is(not(equalTo(null))));
	}
	
	@Test
	public void equals_samePiece_returnsTrue()
	{
		Piece piece = new MockPiece(2, 1);
		assertThat(piece, is(equalTo(piece)));
	}
	
	@Test
	public void equals_differentPieces_returnsFalse()
	{
		Piece one = new MockPiece(1, 1);
		Piece other = new MockPiece(2, 1);

		assertThat(one, is(not(equalTo(other))));
		assertThat(other, is(not(equalTo(one))));
	}
}
