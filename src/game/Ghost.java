package game;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Ghost extends Entity implements Constant{
	protected GhostState state;
	protected GhostState stateMemory;
	protected Position target;
	protected int frightenedTimeLeft;
	protected int pelletToExit;
	protected Position housePosition;
	

	static private Map<Direction, Image> i_eaten = new HashMap<Direction, Image>();
	static private Image i_frightened = new ImageIcon("res\\f.gif").getImage();
	
	static {
		i_eaten.put(Direction.Up, new ImageIcon("res\\e_Up.gif").getImage());
		i_eaten.put(Direction.Down, new ImageIcon("res\\e_Down.gif").getImage());
		i_eaten.put(Direction.Left, new ImageIcon("res\\e_Left.gif").getImage());
		i_eaten.put(Direction.Right, new ImageIcon("res\\e_Right.gif").getImage());
	}
	
	/**
	 * Visszaadja a szellemrõl a megfelelõ képet
	 * 
	 * Visszaadja a szellemrõl a megfelelõ képet a jelenlegi iránya és fázisa alapján
	 * 
	 * @return Image - szellem képe
	 */
	public Image getImage() {
		if(state.equals(GhostState.Frightened))
			return i_frightened;
		if(state.equals(GhostState.Eaten))
			return i_eaten.get(direction);
		return images.get(direction);
	}
	
	
	/**
	 * Szellem konstruktora
	 * 
	 * Leszármazott osztályok használják
	 * 
	 * @param x kezdõpozíció X koordinátája
	 * @param y kezdõpozíció Y koordinátája
	 */
	public Ghost(int x, int y) {
		position = new Position(x,y);
		state = GhostState.Scatter;
	}
	
	/**
	 * Idõ eltelésének hatását reprezentálja a szellemen
	 * 
	 * Ha megfelelõ idõmennyiség eltelt a szellem fázist válthat.
	 * 
	 * @param i eltelet idõ
	 */
	public void timePassed(int i) {
		frightenedTimeLeft -= i;
		if (frightenedTimeLeft < 0 && state.equals(GhostState.Frightened))
		{
			state = stateMemory;
			turnAround();
		}
	}
	
	/**
	 * Target (cél) gettere
	 * 
	 * @return Target
	 */
	public Position getTarget() {
		return target;
	}
	
	/**
	 * Target (cél) settere
	 * 
	 * @param target (cél)
	 */
	public void setTarget(Position target) {
		this.target = target;
	}
	
	/**
	 * State (fázis) gettere
	 * 
	 * @return state (fázis)
	 */
	public GhostState getState() {
		return state;
	}
	
	/**
	 * A fázist átváltja a fázismemóriában lévõ fázisra
	 */
	public void dropState() {
		state = stateMemory;
	}
	
	/**
	 * Meghatározza a szellem következõ fázisát
	 * 
	 * @param s fázis
	 */
	public void setState(GhostState s) {
		
		if(state.equals(GhostState.Eaten) && (s.equals(GhostState.Scatter) || s.equals(GhostState.Chase)))
		{
			stateMemory = s;
			return;
		}
		if(state.equals(GhostState.Eaten) && (s.equals(GhostState.Frightened)))
		{
			return;
		}
		
		
		if(s.equals(GhostState.ExitGhostHouse))
		{
			state = s;
			return;
		}
		
		
		if (s.equals(GhostState.Frightened) && !(state.equals(GhostState.ExitGhostHouse) || state.equals(GhostState.InGhostHouse)))
		{
			if(!state.equals(GhostState.Frightened))
			{
				turnAround();
				stateMemory = state;
			}
			frightenedTimeLeft = FRIGHTENED_TIME;
			state = s;
			return;
		}
		if((state.equals(GhostState.ExitGhostHouse) || state.equals(GhostState.InGhostHouse)) && !s.equals(GhostState.Frightened))
		{
			stateMemory = s;
			return;
		}
		
		state = s;
		
		turnAround();
	}
	
	/**
	 * Meghatározza a szellem irányát
	 * 
	 * Meghatározza a szellem irányát a mezõ alapján amin áll, illetve a fázisa alapján.
	 * 
	 * @param f mezõ amelyen a szellem áll
	 */
	public void choseDirection(Tile f) {
		if(f.isGhostBounce())
		{
			turnAround();
			return;
		}
		if(state.equals(GhostState.Frightened))
		{
			boolean pathFound = false;
			while(!pathFound)
			{
				Random rand = new Random();
				int i = rand.nextInt() % 4;
				switch(i) {
				case 0:
					if(f.getNext(Direction.Up) != null && !direction.equals(Direction.Down))
					{
						direction = Direction.Up;
						pathFound = true;
						break;
					}
				case 1:
					if(f.getNext(Direction.Down) != null && !direction.equals(Direction.Up) && !f.isEnterance())
					{
						direction = Direction.Down;
						pathFound = true;
						break;
					}
				case 2:
					if(f.getNext(Direction.Left) != null && !direction.equals(Direction.Right))
					{
						direction = Direction.Left;
						pathFound = true;
						break;
					}
				case 3:
					if(f.getNext(Direction.Right) != null && !direction.equals(Direction.Left))
					{
						direction = Direction.Right;
						pathFound = true;
						break;
					}
				}
			}
		} else {
			Direction nextDirection = null;
			double min = -1, up, down, left, right;
			if (f.getNext(Direction.Right) != null && !direction.equals(Direction.Left))
			{
				right = f.getNext(Direction.Right).getPosition().measureDistance(target);
				if (right < min || min == -1)
				{
					min = right;
					nextDirection = Direction.Right;
				}
			}
			if (f.getNext(Direction.Down) != null && !direction.equals(Direction.Up) && (!f.isEnterance() || this.state.equals(GhostState.Eaten)))
			{
				down = f.getNext(Direction.Down).getPosition().measureDistance(target);
				if (down < min || min == -1)
				{
					min = down;
					nextDirection = Direction.Down;
				}
			}
			if (f.getNext(Direction.Left) != null && !direction.equals(Direction.Right))
			{
				left = f.getNext(Direction.Left).getPosition().measureDistance(target);
				if (left < min || min == -1)
				{
					min = left;
					nextDirection = Direction.Left;
				}
			}
			if (f.getNext(Direction.Up) != null && !direction.equals(Direction.Down) && !f.isCantGoUp())
			{
				up = f.getNext(Direction.Up).getPosition().measureDistance(target);
				if (up < min || min == -1)
				{
					min = up;
					nextDirection = Direction.Up;
				}
			}
			direction = nextDirection;
		}
			
	}

	/**
	 * PelletToExit gettere
	 * 
	 * PelletToExit: tárolja mennyi pontot kell Pacmanek ennie, hogy a szellem kijöjjön a szellemházból
	 * 
	 * @return PelletToExit
	 */
	public int getPelletToExit() {
		return pelletToExit;
	}

	/**
	 * PelletToExit settere
	 * 
	 * PelletToExit: tárolja mennyi pontot kell Pacmanek ennie, hogy a szellem kijöjjön a szellemházból
	 * 
	 * @param pelletToExit
	 */
	public void setPelletToExit(int pelletToExit) {
		this.pelletToExit = pelletToExit;
	}

	/**
	 * Szellemházbeli pozíció gettere
	 * 
	 * @return housePosition
	 */
	public Position getHousePosition() {
		return housePosition;
	}

	/**
	 * Szellemházbeli pozíció settere
	 * 
	 * @param housePosition
	 */
	public void setHousePosition(Position housePosition) {
		this.housePosition = housePosition;
	}
	
	
	
	
}
