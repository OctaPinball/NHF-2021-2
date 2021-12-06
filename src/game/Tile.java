package game;
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
	


	private Map<Direction, Tile> next;
	
	
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
		next = new HashMap<Direction, Tile>();
	}
	

	/**
	 * Visszadja a mezõ szomszédját a megadott irányban
	 * 
	 * @param d - szomszéd iránya
	 * @return Tile - szomszéd
	 */
	public Tile getNext(Direction d) {
		if (next.containsKey(d))
			return next.get(d);
		return null;
	}
	
	/**
	 * Megadja a mezõ szomszédját a paraméterként kapott irányban
	 * 
	 * @param d - szomszéd iránya
	 * @param t - szomszéd (mezõ)
	 */
	public void setNext(Direction d, Tile t) {
		if (next.containsKey(d))
			next.remove(d);
		next.put(d, t);
	}
	
	/**
	 * Position gettere
	 * 
	 * @return Position
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Megvizsgálja, hogy van-e a mezõn ehetõ pont
	 * 
	 * @return boolean - igaz, ha van rajta ehetõ pont
	 */
	public boolean isHasDot() {
		return hasDot;
	}
	
	/**
	 * Megadja, hogy van-e a mezõn ehetõ pont.
	 * 
	 * @param hasDot - igaz, ha van rajta pont, ellenkezõ esetben hamis
	 */
	public void setHasDot(boolean hasDot) {
		this.hasDot = hasDot;
	}
	
	/**
	 * Megvizsgálja, hogy a mezõ keresztezõdés-e
	 * 
	 * @return boolean - igaz, ha a mezõ keresztezõdés, ellenkezõ esetben hamis
	 */
	public boolean isIntersection() {
		boolean up = false, down = false, left = false, right = false;
		if (next.containsKey(Direction.Up))
			up = true;
		if (next.containsKey(Direction.Down))
			down = true;
		if (next.containsKey(Direction.Left))
			left = true;
		if (next.containsKey(Direction.Right))
			right = true;
		return ((up && left) || (down && right) || (up && right) || (down && left));
	}
	
	/**
	 * Visszadja, hogy a szellemek fordulhatnak-e felfele azon a mezõn
	 * 
	 * @return boolean - igaz, ha felfele is tud fordulni, ellenkezõ esetben hamis
	 */
	public boolean isCantGoUp() {
		return cantGoUp;
	}
	
	/**
	 * Megadja, hogy a szellemek fordulhatnak-e felfele azon a mezõn
	 * 
	 * @param cantGoUp - igaz, ha felfele is tud fordulni, ellenkezõ esetben hamis
	 */
	public void setCantGoUp(boolean cantGoUp) {
		this.cantGoUp = cantGoUp;
	}

	/**
	 * Visszadja, hogy a mezõn van-e nagy pont.
	 * 
	 * @return boolean - igaz, ha van rajta, ellenkezõ esetben hamis
	 */
	public boolean isHasBigDot() {
		return hasBigDot;
	}

	/**
	 * Megadja, hogy a mezõn van-e nagy pont
	 * 
	 * @param hasBigDot - igaz, ha van rajta, ellenkezõ esetben hamis
	 */
	public void setHasBigDot(boolean hasBigDot) {
		this.hasBigDot = hasBigDot;
	}

	/**
	 * Visszadja, hogy a mezõn lehet-e fordulni
	 * 
	 * @return boolean - igaz, ha lehet rajta fordulni, ellenkezõ esetben hamis
	 */
	public boolean isCantTurn() {
		return cantTurn;
	}

	/**
	 * Megadja, hogy a mezõn lehet-e fordulni.
	 * 
	 * @param cantTurn - igaz, ha lehet rajta fordulni, ellenkezõ esetben hamis
	 */
	public void setCantTurn(boolean cantTurn) {
		this.cantTurn = cantTurn;
	}

	/**
	 * Visszadja a teleportmezõ teleport párját
	 * 
	 * @return Tile - teleportmezõ párja
	 */
	Tile getTeleportLink(){
		return teleportLink;
	}
	
	/**
	 * Megadja a teleportmezõ párját
	 * 
	 * @param t - teleportmezõ párja
	 */
	void setTeleportTile(Tile t) {
		teleportLink = t;
	}

	/**
	 * Visszadja, hogy a mezõn meg tudnak-e fordulni (180-as fordulat) a szellemek
	 * 
	 * @return boolean - igaz, ha meg tudnak fordulni, ellenkezõ esetben hamis
	 */
	public boolean isGhostBounce() {
		return ghostBounce;
	}

	/**
	 * Megadja, hogy a mezõn meg tudnak-e fordulni (180-as fordulat) a szellemek
	 * 
	 * @param ghostBounce - igaz, ha meg tudnak fordulni, ellenkezõ esetben hamis
	 */
	public void setGhostBounce(boolean ghostBounce) {
		this.ghostBounce = ghostBounce;
	}

	/**
	 * Visszadja, hogy a mezõ a szellemház bejárata-e
	 * 
	 * @return boolean - igaz, ha bejárat, ellenkezõ esetben hamis
	 */
	public boolean isEnterance() {
		return enterance;
	}

	/**
	 * Megadja, hogy a mezõ a szellemház bejárata-e
	 * 
	 * @param enterance - igaz, ha bejárat, ellenkezõ esetben hamis
	 */
	public void setEnterance(boolean enterance) {
		this.enterance = enterance;
	}

	/**
	 * Visszadja, hogy a mezõn meg tud-e fordulni (180-as fordulat) Pinky
	 * 
	 * @return boolean - igaz, ha meg tud fordulni, ellenkezõ esetben hamis
	 */
	public boolean isPinkyBounce() {
		return pinkyBounce;
	}

	/**
	 * Megadja, hogy a mezõn meg tud-e fordulni (180-as fordulat) Pinky
	 * 
	 * @param pinkyBounce - igaz, ha meg tud fordulni, ellenkezõ esetben hamis
	 */
	public void setPinkyBounce(boolean pinkyBounce) {
		this.pinkyBounce = pinkyBounce;
	}
}
