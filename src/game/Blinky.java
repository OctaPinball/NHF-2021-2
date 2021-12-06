package game;


import javax.swing.ImageIcon;

public class Blinky extends Ghost{

	
	
	/**
	 * Blinky konstruktora
	 * 
	 * L�trehozza blinkyt, meghat�rozza a kezd�poz�ci�j�t, kezd�f�zis�t, kezd�ir�ny�t, �s a szellemh�zban l�v� �jra�led�s�nek poz�ci�j�t.
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
	 * Meghat�rozza blinky legk�zelebbi c�lj�t
	 * 
	 * Meghat�rozza blinky legk�zelebbi c�lj�t a jelenlegi f�zisa alapj�n, illetve Pacman poz�ci�ja alapj�n
	 * 
	 * @param p Pacman referenci�ja
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
