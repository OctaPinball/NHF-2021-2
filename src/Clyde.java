

public class Clyde extends Ghost{

	public Clyde() {
		super(CLYDE_GHOSTHOUSE.getX(), CLYDE_GHOSTHOUSE.getY());
		setPelletToExit(CLYDE_DOT);
		setState(GhostState.InGhostHouse);
		housePosition = CLYDE_GHOSTHOUSE;
	}
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(Ghost.GhostState.Chase))
			if(p.getPosition().measureDistance(this.getPosition()) > 160)
				target = p.getPosition();
			else
				target = new Position(0, 720);
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(0, 720);
	}
}
