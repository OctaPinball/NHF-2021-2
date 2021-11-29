

public class Blinky extends Ghost{

	public Blinky() {
		super(BLINKY_GHOSTHOUSE.getX(), BLINKY_GHOSTHOUSE.getY());
		setState(GhostState.Scatter);
		direction = Game.Direction.Right;
	}
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(Ghost.GhostState.Chase))
			target = p.getPosition();
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(560, 0);
	}

}
