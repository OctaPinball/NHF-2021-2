
public class Blinky extends Ghost{

	public Blinky(int x, int y) {
		super(x, y);
	}
	public void detTarget(PacMan p) {
		if (state.equals(Ghost.GhostState.Chase))
			target = p.getPosition();
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(560, 0);
	}

}
