import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Tile {
	private Position position;
	private boolean turnPoint;
	private boolean hasDot;
	private boolean hasBigDot;
	private boolean cantGoUp;
	private boolean cantTurn;
	Tile teleportLink;

	private Image dotImage;
	private Map<Game.Direction, Tile> next;
	
	
	public Tile(int x, int y, int constructionCode){
		position = new Position(x, y);
		setHasDot(false);
		setCantGoUp(false);
		setHasBigDot(false);
		setCantTurn(false);
		teleportLink = null;
		if(constructionCode == 1 || constructionCode == 4)
			setHasDot(true);
		if(constructionCode == 4 || constructionCode == 5)
			setCantGoUp(true);
		if(constructionCode == 3)
			setHasBigDot(true);
		if(constructionCode == 6)
			setCantTurn(true);
		next = new HashMap<Game.Direction, Tile>();
	}
	
	public Tile getNext(Game.Direction d) {
		if (next.containsKey(d))
			return next.get(d);
		return null;
	}
	
	
	public void setNext(Game.Direction d, Tile t) {
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
}
