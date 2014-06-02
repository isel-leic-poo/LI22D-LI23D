package poo.demos.puzzle.model.tests.mocks;

import poo.demos.puzzle.model.Piece;
import poo.demos.puzzle.model.Position;

/** 
 * Mock class used to enable testing of the Piece abstract class.
 * The abstract class implements functionality that deserves independent
 * testing.
 */
public class MockPiece extends Piece
{
	private final Position initial;
	
	public MockPiece(int x, int y) { this(Position.fromCoordinates(x, y)); }
	
	public MockPiece(Position position) { initial = position; }

	@Override
	public Position getInitialPosition() { return initial; }

	@Override
	public Position getPosition() { return getInitialPosition(); }
}