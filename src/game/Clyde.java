package game;

import javax.swing.ImageIcon;

public class Clyde extends Ghost{

	/**
	 * Clyde konstruktora
	 * 
	 * Létrehozza clyde-ot, meghatározza a kezdõpozícióját, kezdõfázisát, kezdõirányát, és a szellemházban lévõ újraéledésének pozícióját.
	 * 
	 */
	public Clyde() {
		super(CLYDE_GHOSTHOUSE.getX(), CLYDE_GHOSTHOUSE.getY());
		setPelletToExit(CLYDE_DOT);
		setState(GhostState.InGhostHouse);
		housePosition = CLYDE_GHOSTHOUSE;
        setImage(new ImageIcon("res\\c_up.gif").getImage(), Direction.Up);
        setImage(new ImageIcon("res\\c_down.gif").getImage(), Direction.Down);
        setImage(new ImageIcon("res\\c_left.gif").getImage(), Direction.Left);
        setImage(new ImageIcon("res\\c_right.gif").getImage(), Direction.Right);
	}
	
	/**
	 * Meghatározza clyde legközelebbi célját
	 * 
	 * Meghatározza clyde legközelebbi célját a jelenlegi fázisa alapján, illetve Pacman pozíciója alapján
	 * 
	 * @param p Pacman referenciája
	 */
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(GhostState.Chase))
			if(p.getPosition().measureDistance(this.getPosition()) > 160)
				target = p.getPosition();
			else
				target = new Position(0, 720);
		if (state.equals(GhostState.Scatter))
			target = new Position(0, 720);
	}
}
