
public class PacMan extends Entity implements Constant{
	
	private Game.Direction nextDirection = Game.Direction.Left;
	
	public PacMan(int x, int y){
		position = new Position(x,y);
	}
	
	public void setDirection(Game.Direction direction) {
		nextDirection = direction;
	}

	
	public void movePacMan(Tile t) {
		if (t.getNext(direction) != null)
			move(MOVE_STEPS);
	}
	
	public void turn(Tile t) {
		if(!t.isCantTurn())
		{
			if(!(t.isEnterance() && nextDirection.equals(Game.Direction.Down)))
				if (t.getNext(nextDirection) != null)
					direction = nextDirection;
		}
	}
}
