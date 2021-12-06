package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


import game.Position;
import game.Direction;

public class PositionTest {
	

	Position p1;
	Position p2;
	Position p3;
	Position p4;
	
	
	@Before
	public void setUp() {
		p1 = new Position(0,0);
		p2 = new Position(20,0);
		p3 = new Position(0,0);
		p4 = new Position(320,440);
	}
	@Test
	public void testEquals1() throws Exception{
		assertTrue("Hibás egyenlõség vizsgálat!",p1.equals(p3));
	}
	
	@Test
	public void testEquals2() throws Exception{
		assertTrue("Hibás egyenlõség vizsgálat!",p1.equals(p2,20,0));
	}
	
	@Test
	public void testGetX() throws Exception{
		assertEquals("Hibás getX függvény!",p4.getX(), 320);
	}
	
	@Test
	public void testGetY() throws Exception{
		assertEquals("Hibás getY függvény!",p4.getY(), 440);
	}
	
	@Test
	public void testMesureDistance() throws Exception{
		assertEquals("Hibás getY függvény!",p4.measureDistance(p1), 544, 0.5);
	}
	
	@Test
	public void testWrongOffset() throws Exception{
		p1 = p1.wrongPositionOffset(Direction.Up, 20);
		Position p6 = new Position(-20,-20);
		assertTrue("Hibás wrongPositionOffset függvény!",p1.equals(p6));
	}
	
	@Test
	public void testPointMirror() throws Exception{
		p1 = p1.pointMirror(p2);
		Position p6 = new Position(40,0);
		assertTrue("Hibás poitnMirror függvény!",p1.equals(p6));
	}
	
}
