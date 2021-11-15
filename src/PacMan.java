
public class PacMan extends Entity{
	
	private Game.Direction nextDirection = Game.Direction.Left;
	
	public PacMan(int x, int y){
		position = new Position(x,y);
	}
	
	public void setDirection(Game.Direction direction) {
		nextDirection = direction;
	}

	
	public void movePacMan(Tile t) {
		if (t.getNext(direction) != null)
			move();
	}
	
	public void turn(Tile t) {
		if(!t.isCantTurn())
		{
			if (t.getNext(nextDirection) != null)
				direction = nextDirection;
		}
	}
}
