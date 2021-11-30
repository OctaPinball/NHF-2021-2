import java.util.HashMap;
import java.util.Map;


public class Tile {
	private Position position;
	private boolean hasDot;
	private boolean hasBigDot;
	private boolean cantGoUp;
	private boolean cantTurn;
	private boolean ghostBounce;
	private boolean enterance;
	private boolean pinkyBounce;
	Tile teleportLink;


	private Map<Game.Direction, Tile> next;
	
	
	public Tile(int x, int y, int constructionCode){
		position = new Position(x, y);
		setHasDot(false);
		setCantGoUp(false);
		setHasBigDot(false);
		setCantTurn(false);
		setEnterance(false);
		setGhostBounce(false);
		setPinkyBounce(false);
		
		teleportLink = null;
		if(constructionCode == 1 || constructionCode == 4)
			setHasDot(true);
		if(constructionCode == 4 || constructionCode == 5)
			setCantGoUp(true);
		if(constructionCode == 3)
			setHasBigDot(true);
		if(constructionCode == 6)
			setCantTurn(true);
		if(constructionCode == 7)
			setEnterance(true);
		if(constructionCode == 8)
			setGhostBounce(true);
		if(constructionCode == 9)
			setPinkyBounce(true);
		next = new HashMap<Game.Direction, Tile>();
	}
	
	public void setDot(int i) {
		if(i == 1 || i == 4)
			setHasDot(true);
		if(i == 3)
			setHasBigDot(true);
	}
	
	public Tile getNext(Game.Direction d) {
		if (next.containsKey(d))
			return next.get(d);
		return null;
	}
	
	
	public void setNext(Game.Direction d, Tile t) {
		if (next.containsKey(d))
			next.remove(d);
		next.put(d, t);
	}
	
	
	public Position getPosition() {
		return position;
	}
	
	
	public boolean isHasDot() {
		return hasDot;
	}
	
	
	public void setHasDot(boolean hasDot) {
		this.hasDot = hasDot;
	}
	
	
	public boolean isIntersection() {
		boolean up = false, down = false, left = false, right = false;
		if (next.containsKey(Game.Direction.Up))
			up = true;
		if (next.containsKey(Game.Direction.Down))
			down = true;
		if (next.containsKey(Game.Direction.Left))
			left = true;
		if (next.containsKey(Game.Direction.Right))
			right = true;
		return ((up && left) || (down && right) || (up && right) || (down && left));
	}
	
	public int countN() {
		int i = 0;
		if (next.containsKey(Game.Direction.Up))
			i++;
		if (next.containsKey(Game.Direction.Down))
			i++;
		if (next.containsKey(Game.Direction.Left))
			i++;
		if (next.containsKey(Game.Direction.Right))
			i++;
		return i;
	}
	
	
	public boolean isCantGoUp() {
		return cantGoUp;
	}
	
	
	public void setCantGoUp(boolean cantGoUp) {
		this.cantGoUp = cantGoUp;
	}


	public boolean isHasBigDot() {
		return hasBigDot;
	}


	public void setHasBigDot(boolean hasBigDot) {
		this.hasBigDot = hasBigDot;
	}


	public boolean isCantTurn() {
		return cantTurn;
	}


	public void setCantTurn(boolean cantTurn) {
		this.cantTurn = cantTurn;
	}

	Tile getTeleportLink(){
		return teleportLink;
	}
	void setTeleportTile(Tile t) {
		teleportLink = t;
	}

	public boolean isGhostBounce() {
		return ghostBounce;
	}

	public void setGhostBounce(boolean ghostBounce) {
		this.ghostBounce = ghostBounce;
	}

	public boolean isEnterance() {
		return enterance;
	}

	public void setEnterance(boolean enterance) {
		this.enterance = enterance;
	}

	public boolean isPinkyBounce() {
		return pinkyBounce;
	}

	public void setPinkyBounce(boolean pinkyBounce) {
		this.pinkyBounce = pinkyBounce;
	}
}
