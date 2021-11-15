
public class Inky extends Ghost{

	public Inky(int x, int y) {
		super(x, y);
	}
	public void detTarget(PacMan p, Blinky b) {
		if (state.equals(Ghost.GhostState.Chase))
			target = b.getPosition().pointMirror(p.getPosition());
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(560, 720);
	}
}
