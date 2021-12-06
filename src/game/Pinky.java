package game;

import javax.swing.ImageIcon;

public class Pinky extends Ghost{
	/**
	 * Pinky konstruktora
	 * 
	 * L�trehozza pinkyt, meghat�rozza a kezd�poz�ci�j�t, kezd�f�zis�t, kezd�ir�ny�t, �s a szellemh�zban l�v� �jra�led�s�nek poz�ci�j�t.
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
	 * Meghat�rozza pinky legk�zelebbi c�lj�t
	 * 
	 * Meghat�rozza pinky legk�zelebbi c�lj�t a jelenlegi f�zisa alapj�n, illetve Pacman poz�ci�ja alapj�n
	 * 
	 * @param p Pacman referenci�ja
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
