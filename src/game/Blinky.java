package game;


import javax.swing.ImageIcon;

public class Blinky extends Ghost{

	
	
	/**
	 * Blinky konstruktora
	 * 
	 * Létrehozza blinkyt, meghatározza a kezdõpozícióját, kezdõfázisát, kezdõirányát, és a szellemházban lévõ újraéledésének pozícióját.
	 * 
	 */
	public Blinky() {
		super(BLINKY_GHOSTHOUSE.getX(), BLINKY_GHOSTHOUSE.getY());
		setState(GhostState.Scatter);
		direction = Direction.Right;
		housePosition = PINKY_GHOSTHOUSE;
        setImage(new ImageIcon("res\\b_up.gif").getImage(), Direction.Up);
        setImage(new ImageIcon("res\\b_down.gif").getImage(), Direction.Down);
        setImage(new ImageIcon("res\\b_left.gif").getImage(), Direction.Left);
        setImage(new ImageIcon("res\\b_right.gif").getImage(), Direction.Right);
	}
	
	/**
	 * Meghatározza blinky legközelebbi célját
	 * 
	 * Meghatározza blinky legközelebbi célját a jelenlegi fázisa alapján, illetve Pacman pozíciója alapján
	 * 
	 * @param p Pacman referenciája
	 */
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(GhostState.Chase))
			target = p.getPosition();
		if (state.equals(GhostState.Scatter))
			target = new Position(560, 0);
	}

}
