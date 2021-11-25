

public class Pinky extends Ghost{

	public Pinky(int x, int y) {
		super(x, y);
	}
	public void detTarget(PacMan p) {
		if (state.equals(Ghost.GhostState.Chase))
			target = p.getPosition().wrongPositionOffset(p.getDirection(), 80);
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(0, 0);
	}
	
}
