package game;

public interface Constant {
	final int PANEL_HEIGH = 720;
	final int PANEL_WIDH = 560;
	final int MOVE_STEPS = 2;
	final int TIMER_MS = 10;
	final int START_LIFE = 1;
	final int FRIGHTENED_TIME = 10000;
	final int CLYDE_DOT = 60;
	final int INKY_DOT = 30;
	final int GHOST_EAT_SCORE[] = {200,400,800,1600};
	
	final Position INKY_GHOSTHOUSE = new Position(230,340);
	final Position PINKY_GHOSTHOUSE = new Position(270,340);
	final Position CLYDE_GHOSTHOUSE = new Position(310,340);
	final Position BLINKY_GHOSTHOUSE = new Position(270,280);
	final Position GHOSTHOUSE = new Position(270,280);
	
	final int SCATTER_TIME[][] = {
			{7000,7000,5000,5000},
			{7000,7000,5000,1000},
			{5000,5000,5000,1000}
	};
	final int CHASE_TIME[][] = {
			{20000,20000,20000},
			{20000,20000,353000},
			{20000,20000,357000}
	};
	
	/**
	 * 0 = no tile,
	 *
	 * 1 = tile with dot,
	 *
	 * 2 = tile without dot,
	 *
	 * 3 = tile with big dot,
	 *
	 * 4 = tile with dot without upway,
	 *
	 * 5 = tile without dot without upway,
	 *
	 * 6 = cant turn,
	 *
	 * 7 = ghosthouse enter,
	 *
	 * 8 = ghost can turn,
	 *
	 * 9 = ghosthouse inner gate
	 *
	 */
	final int TILE_HELPER[][] = 
		{
	{1,1,1,1,1,1,1,1,1,1,1,1,0},
	{1,0,0,0,0,1,0,0,0,0,0,1,0},
	{3,0,0,0,0,1,0,0,0,0,0,1,0},
	{1,0,0,0,0,1,0,0,0,0,0,1,0},
	{1,1,1,1,1,1,1,1,1,1,1,1,1},
	{1,0,0,0,0,1,0,0,1,0,0,0,0},
	{1,0,0,0,0,1,0,0,1,0,0,0,0},
	{1,1,1,1,1,1,0,0,1,1,1,1,0},
	{0,0,0,0,0,1,0,0,0,0,0,2,0},
	{0,0,0,0,0,1,0,0,0,0,0,2,0},
	{0,0,0,0,0,1,0,0,2,2,2,5,2},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{6,6,6,6,6,1,2,2,2,0,0,0,0},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{0,0,0,0,0,1,0,0,2,2,2,2,2},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{0,0,0,0,0,1,0,0,2,0,0,0,0},
	{1,1,1,1,1,1,1,1,1,1,1,1,0},
	{1,0,0,0,0,1,0,0,0,0,0,1,0},
	{1,0,0,0,0,1,0,0,0,0,0,1,0},
	{3,1,1,0,0,1,1,1,1,1,1,4,2},
	{0,0,1,0,0,1,0,0,1,0,0,0,0},
	{0,0,1,0,0,1,0,0,1,0,0,0,0},
	{1,1,1,1,1,1,0,0,1,1,1,1,0},
	{1,0,0,0,0,0,0,0,0,0,0,1,0},
	{1,0,0,0,0,0,0,0,0,0,0,1,0},
	{1,1,1,1,1,1,1,1,1,1,1,1,1},
};
			
}
