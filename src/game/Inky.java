package game;

import javax.swing.ImageIcon;

public class Inky extends Ghost{

	/**
	 * Inky konstruktora
	 * 
	 * L�trehozza inkyt, meghat�rozza a kezd�poz�ci�j�t, kezd�f�zis�t, kezd�ir�ny�t, �s a szellemh�zban l�v� �jra�led�s�nek poz�ci�j�t.
	 * 
	 */
	public Inky() {
		super(INKY_GHOSTHOUSE.getX(), INKY_GHOSTHOUSE.getY());
		setPelletToExit(INKY_DOT);
		setState(GhostState.InGhostHouse);
		housePosition = INKY_GHOSTHOUSE;
        setImage(new ImageIcon("res\\i_up.gif").getImage(), Direction.Up);
        setImage(new ImageIcon("res\\i_down.gif").getImage(), Direction.Down);
        setImage(new ImageIcon("res\\i_left.gif").getImage(), Direction.Left);
        setImage(new ImageIcon("res\\i_right.gif").getImage(), Direction.Right);
	}
	
	
	/**
	 * Meghat�rozza inky legk�zelebbi c�lj�t
	 * 
	 * Meghat�rozza inky legk�zelebbi c�lj�t a jelenlegi f�zisa alapj�n, illetve Pacman poz�ci�ja alapj�n
	 * 
	 * @param p Pacman referenci�ja
	 */
	public void detTarget(PacMan p, Blinky b) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(GhostState.Chase))
			target = b.getPosition().pointMirror(p.getPosition());
		if (state.equals(GhostState.Scatter))
			target = new Position(560, 720);
	}
}
