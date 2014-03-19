package poo.demos.puzzle.model.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import poo.demos.puzzle.model.*;

public class PieceTests {

	@Test
	public void testCorrectInitiation() 
	{
		Piece p1 = new Piece(2, 3);

		assertEquals(2, p1.getX());
		assertEquals(3, p1.getY());
		assertTrue(p1.isAtCorrectPosition());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidInitiation()
	{
		new Piece(-1, 3);
	}
	
	@Test
	public void testSetAtValidPosition()
	{
		Piece p1 = new Piece(0, 1);
		
		p1.setPosition(1, 1);
		assertEquals(1, p1.getX());
		assertEquals(1, p1.getY());
		assertFalse(p1.isAtCorrectPosition());
		
		p1.setPosition(2, 2);
		assertEquals(2, p1.getX());
		assertEquals(2, p1.getY());
		assertFalse(p1.isAtCorrectPosition());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetAtInvalidPosition()
	{
		Piece p1 = new Piece(0, 1);
		
		p1.setPosition(-1, 1);
	}
	
	@Test
	public void testMoveToAValidPosition()
	{
		Piece p1 = new Piece(0, 1);
		p1.move(1, 1);
		assertFalse(p1.isAtCorrectPosition());
		p1.move(0, -1);
		assertFalse(p1.isAtCorrectPosition());
		p1.move(-1, 0);
		assertTrue(p1.isAtCorrectPosition());
	}

	@Test(expected = IllegalStateException.class)
	public void testMoveToAnInvalidPosition()
	{
		Piece p1 = new Piece(0, 1);
		p1.move(-1, 1);
	}
}
