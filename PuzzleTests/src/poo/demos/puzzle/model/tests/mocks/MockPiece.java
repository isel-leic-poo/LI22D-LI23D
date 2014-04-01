package poo.demos.puzzle.model.tests.mocks;

import poo.demos.puzzle.model.Piece;

/** 
 * Mock class used to enable testing of the Piece abstract class.
 * The abstract class implements functionality that deserves independent
 * testing.
 */
public class MockPiece extends Piece
{
	public MockPiece(int x, int y) { super(x, y); }

	@Override
	public int getX() { return getInitialX(); }

	@Override
	public int getY() { return getInitialY(); }
}