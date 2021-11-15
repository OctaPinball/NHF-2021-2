
public class Clyde extends Ghost{

	public Clyde(int x, int y) {
		super(x, y);
	}
	public void detTarget(PacMan p) {
		if (state.equals(Ghost.GhostState.Chase))
			if(p.getPosition().measureDistance(this.getPosition()) > 160)
				target = p.getPosition();
			else
				target = new Position(0, 720);
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(0, 720);
	}
}
