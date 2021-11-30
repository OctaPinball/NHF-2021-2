import java.util.Random;

public class Ghost extends Entity implements Constant{
	enum GhostState {
		  Scatter,
		  Chase,
		  Frightened,
		  Eaten,
		  InGhostHouse,
		  ExitGhostHouse
		}
	protected GhostState state;
	protected GhostState stateMemory;
	protected Position target;
	protected int frightenedTimeLeft;
	protected int pelletToExit;
	protected Position housePosition;
	
	public Ghost(int x, int y) {
		position = new Position(x,y);
		state = GhostState.Chase;
	}
	
	
	public void timePassed(int i) {
		frightenedTimeLeft -= i;
		if (frightenedTimeLeft < 0 && state.equals(GhostState.Frightened))
		{
			state = stateMemory;
			turnAround();
		}
	}
	
	public Position getTarget() {
		return target;
	}
	public void setTarget(Position target) {
		this.target = target;
	}
	
	public GhostState getState() {
		return state;
	}
	
	public void dropState() {
		System.out.println("drop");
		state = stateMemory;
	}
	
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
		
		
		/*
		
		if (s.equals(GhostState.ExitGhostHouse) && (state.equals(GhostState.InGhostHouse) || state.equals(GhostState.Eaten)))
		{
				state = s;
				return;
		}
		if (state.equals(GhostState.ExitGhostHouse) || state.equals(GhostState.InGhostHouse))
		{
			if (s.equals(GhostState.Chase) || s.equals(GhostState.Scatter) || s.equals(GhostState.Frightened))
			{
					stateMemory = s;
					return;
			}
		}
		if (s.equals(GhostState.Frightened))
		{
			if(!state.equals(GhostState.Frightened))
			{
				turnAround();
			}
			frightenedTimeLeft = FRIGHTENED_TIME;
			stateMemory = state;
			state = s;
			return;
		}
		if (state.equals(GhostState.Frightened) && s.equals(GhostState.Eaten))
		{
			state = s;
		}
		if (state.equals(GhostState.Frightened) && !s.equals(GhostState.Frightened))
		{
			stateMemory = s;
			return;
		}
		state = s;
		*/
		
		turnAround();
	}
	public void choseDirection(Tile f) {
		if(f.isGhostBounce())
		{
			turnAround();
			System.out.println("ez");
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
					if(f.getNext(Game.Direction.Up) != null && !direction.equals(Game.Direction.Down))
					{
						direction = Game.Direction.Up;
						pathFound = true;
						break;
					}
				case 1:
					if(f.getNext(Game.Direction.Down) != null && !direction.equals(Game.Direction.Up) && !f.isEnterance())
					{
						direction = Game.Direction.Down;
						pathFound = true;
						break;
					}
				case 2:
					if(f.getNext(Game.Direction.Left) != null && !direction.equals(Game.Direction.Right))
					{
						direction = Game.Direction.Left;
						pathFound = true;
						break;
					}
				case 3:
					if(f.getNext(Game.Direction.Right) != null && !direction.equals(Game.Direction.Left))
					{
						direction = Game.Direction.Right;
						pathFound = true;
						break;
					}
				}
			}
		} else {
			Game.Direction nextDirection = null;
			double min = -1, up, down, left, right;
			if (f.getNext(Game.Direction.Right) != null && !direction.equals(Game.Direction.Left))
			{
				right = f.getNext(Game.Direction.Right).getPosition().measureDistance(target);
				if (right < min || min == -1)
				{
					min = right;
					nextDirection = Game.Direction.Right;
				}
			}
			if (f.getNext(Game.Direction.Down) != null && !direction.equals(Game.Direction.Up) && (!f.isEnterance() || this.state.equals(GhostState.Eaten)))
			{
				down = f.getNext(Game.Direction.Down).getPosition().measureDistance(target);
				if (down < min || min == -1)
				{
					min = down;
					nextDirection = Game.Direction.Down;
				}
			}
			if (f.getNext(Game.Direction.Left) != null && !direction.equals(Game.Direction.Right))
			{
				left = f.getNext(Game.Direction.Left).getPosition().measureDistance(target);
				if (left < min || min == -1)
				{
					min = left;
					nextDirection = Game.Direction.Left;
				}
			}
			if (f.getNext(Game.Direction.Up) != null && !direction.equals(Game.Direction.Down) && !f.isCantGoUp())
			{
				up = f.getNext(Game.Direction.Up).getPosition().measureDistance(target);
				if (up < min || min == -1)
				{
					min = up;
					nextDirection = Game.Direction.Up;
				}
			}
			direction = nextDirection;
		}
			
	}


	public int getPelletToExit() {
		return pelletToExit;
	}


	public void setPelletToExit(int pelletToExit) {
		this.pelletToExit = pelletToExit;
	}


	public Position getHousePosition() {
		return housePosition;
	}


	public void setHousePosition(Position housePosition) {
		this.housePosition = housePosition;
	}
	
	
	
	
}
