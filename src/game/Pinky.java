package game;

import javax.swing.ImageIcon;

public class Pinky extends Ghost{
	/**
	 * Pinky konstruktora
	 * 
	 * Létrehozza pinkyt, meghatározza a kezdõpozícióját, kezdõfázisát, kezdõirányát, és a szellemházban lévõ újraéledésének pozícióját.
	 * 
	 */
	public Pinky() {
		super(PINKY_GHOSTHOUSE.getX(), PINKY_GHOSTHOUSE.getY());
		setState(GhostState.ExitGhostHouse);
		direction = Direction.Up;
		housePosition = PINKY_GHOSTHOUSE;
        setImage(new ImageIcon("res\\p_up.gif").getImage(), Direction.Up);
        setImage(new ImageIcon("res\\p_down.gif").getImage(), Direction.Down);
        setImage(new ImageIcon("res\\p_left.gif").getImage(), Direction.Left);
        setImage(new ImageIcon("res\\p_right.gif").getImage(), Direction.Right);
	}
	
	/**
	 * Meghatározza pinky legközelebbi célját
	 * 
	 * Meghatározza pinky legközelebbi célját a jelenlegi fázisa alapján, illetve Pacman pozíciója alapján
	 * 
	 * @param p Pacman referenciája
	 */
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(GhostState.Chase))
			target = p.getPosition().wrongPositionOffset(p.getDirection(), 80);
		if (state.equals(GhostState.Scatter))
			target = new Position(0, 0);
	}
	
}
