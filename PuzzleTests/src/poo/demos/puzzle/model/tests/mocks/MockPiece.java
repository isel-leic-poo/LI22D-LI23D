package poo.demos.puzzle.model.tests.mocks;

import poo.demos.puzzle.model.Piece;

/** 
 * Mock class used to enable testing of the Piece abstract class.
 * The abstract class implements functionality that deserves independent
 * testing.
 */
public class MockPiece extends Piece
{
	private final int initialX, initialY;
	
	public MockPiece(int x, int y) 
	{
		initialX = x;
		initialY = y;
	}

	@Override
	public int getInitialX() { return initialX; }

	@Override
	public int getInitialY() { return initialY; }

	@Override
	public int getX() { return getInitialX(); }

	@Override
	public int getY() { return getInitialY(); }
}