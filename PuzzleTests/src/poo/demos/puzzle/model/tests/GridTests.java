package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import poo.demos.puzzle.model.Grid;
import poo.demos.puzzle.model.Piece;

public class GridTests {

	@Test
	public void testRandomPuzzleCreation() 
	{
		final int SIDE = 4;
		
		Grid puzzle = Grid.createRandomPuzzle(SIDE);
		assertNotNull(puzzle);
		
		int pieceCount = 0;
		for(int y = 0; y < SIDE; ++y)
		{
			for(int x = 0; x < SIDE; ++x)
			{
				Piece p = puzzle.getPieceAtPosition(x, y);
				if(p != null) 
				{
					pieceCount += 1;
					assertEquals(x, p.getX());
					assertEquals(y, p.getY());
				}
			}
		}
		
		assertEquals(SIDE * SIDE - 1, pieceCount);
	}
}
