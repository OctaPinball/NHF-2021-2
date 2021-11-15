
public class Ghost extends Entity{
	enum GhostState {
		  Scatter,
		  Chase,
		  Frightened,
		  Eaten
		}
	protected GhostState state;
	protected Position target;
	
	public Ghost(int x, int y) {
		position = new Position(x,y);
		state = GhostState.Chase;
	}
	
	public Position getTarget() {
		return target;
	}
	public void setTarget(Position target) {
		this.target = target;
	}
	public void choseDirection(Tile f) {
		//if(!f.isCantTurn()) {
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
			if (f.getNext(Game.Direction.Down) != null && !direction.equals(Game.Direction.Up))
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
		//}
	}
}
