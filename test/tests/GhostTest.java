package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Blinky;
import game.GhostState;


public class GhostTest {

	@Test
	public void testConst() throws Exception{
		Blinky g1 = new Blinky();
		assertTrue("Hibás konstruktor", g1.getState().equals(GhostState.Scatter));
	}

}
