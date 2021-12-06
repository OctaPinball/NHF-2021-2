package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.Direction;
import game.Tile;

public class TileTest {

	Tile t1 = new Tile(0,0,2);
	Tile t2 = new Tile(0,0,1);
	Tile t3 = new Tile(20,0,1);
	Tile t4 = new Tile(0,20,1);
	
	@Before
	public void setUp() {

	}
	@Test
	public void testHasDot() throws Exception{
		assertFalse("Hib�s konstruktor!",t1.isHasDot());
		assertTrue("Hib�s konstruktor!",t2.isHasDot());
	}
	@Test
	public void testNext() throws Exception{
		t1.setNext(Direction.Down, t4);
		t1.setNext(Direction.Right, t3);
		assertEquals("Hib�s next!",t1.getNext(Direction.Down), t4);
		assertEquals("Hib�s next!",t1.getNext(Direction.Right), t3);
	}
	

}
