package game;

import javax.swing.ImageIcon;

public class PacMan extends Entity implements Constant{
	
	private Direction nextDirection = Direction.Left;
	
	public PacMan(int x, int y){
		position = new Position(x,y);
		setImage(new ImageIcon("res\\up.gif").getImage(), Direction.Up);
        setImage(new ImageIcon("res\\down.gif").getImage(), Direction.Down);
        setImage(new ImageIcon("res\\left.gif").getImage(), Direction.Left);
        setImage(new ImageIcon("res\\right.gif").getImage(), Direction.Right);
		
	}
	
	/**
	 * A jelenlegi ir�ny settere
	 * 
	 * @param direction
	 */
	public void setDirection(Direction direction) {
		nextDirection = direction;
	}

	/**
	 * Elmozdatja Pacmant a megfelel� ir�nyba
	 * 
	 * @param t - mez� amelyen Pacman �ll
	 */
	public void movePacMan(Tile t) {
		if (t.getNext(direction) != null)
			move(MOVE_STEPS);
	}
	
	/**
	 * Megvizsg�lja hogy Pacman tud-e fordulni, �s ha igen a fordulatot v�grehajtja.
	 * 
	 * @param t - mez� amelyen Pacman �ll
	 */
	public void turn(Tile t) {
		if(!t.isCantTurn())
		{
			if(!(t.isEnterance() && nextDirection.equals(Direction.Down)))
				if (t.getNext(nextDirection) != null)
					direction = nextDirection;
		}
	}
}
