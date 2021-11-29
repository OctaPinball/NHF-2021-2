
public class Inky extends Ghost{

	public Inky() {
		super(INKY_GHOSTHOUSE.getX(), INKY_GHOSTHOUSE.getY());
		setPelletToExit(INKY_DOT);
		setState(GhostState.InGhostHouse);
		housePosition = INKY_GHOSTHOUSE;
	}
	public void detTarget(PacMan p, Blinky b) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(Ghost.GhostState.Chase))
			target = b.getPosition().pointMirror(p.getPosition());
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(560, 720);
	}
}
