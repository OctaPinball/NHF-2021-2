

public class Pinky extends Ghost{

	public Pinky() {
		super(PINKY_GHOSTHOUSE.getX(), PINKY_GHOSTHOUSE.getY());
		setState(GhostState.ExitGhostHouse);
		direction = Game.Direction.Up;
		housePosition = PINKY_GHOSTHOUSE;
	}
	public void detTarget(PacMan p) {
		if(getState().equals(GhostState.InGhostHouse) || getState().equals(GhostState.Eaten))
			target = getHousePosition();
		if(getState().equals(GhostState.ExitGhostHouse))
			target = new Position(GHOSTHOUSE.getX(), GHOSTHOUSE.getY());
		if (state.equals(Ghost.GhostState.Chase))
			target = p.getPosition().wrongPositionOffset(p.getDirection(), 80);
		if (state.equals(Ghost.GhostState.Scatter))
			target = new Position(0, 0);
	}
	
}
