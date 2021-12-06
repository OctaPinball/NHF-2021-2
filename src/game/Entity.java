package game;

import java.util.HashMap;
import java.util.Map;
import java.awt.Image;

public abstract class Entity {
	protected Position position;
	protected Direction direction = Direction.Left;
	protected Tile lastTile = null;
	protected Map<Direction, Image> images = new HashMap<Direction, Image>();;

	
	/**
	 * Visszaadja az iránynak megfelelõ képet
	 * 
	 * @return Image - entitás képe
	 */
	public Image getImage() {
		return images.get(direction);
	}
	
	/**
	 * Hozzáad egy képet az entitáshoz iránynak megfelõen
	 * 
	 * @param i - hozzáadni kívánt kép
	 * @param d - irány melyre a kép meg fog jelenni
	 */
	public void setImage(Image i, Direction d) {
		images.put(d, i);
	}
	
	/**
	 * Jelenlegi pozíció gettere
	 * 
	 * @return position - jelenlegi pozíció
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Jelenlegi pozíció settere
	 * 
	 * @param p - jelenlegi pozíció
	 */
	public void setPosition(Position p) {
		position.setX(p.getX());
		position.setY(p.getY());
	}
	
	/**
	 * Utolsó mezõ settere
	 * 
	 * @param t - utolsó mezõ melyen az entitás volt
	 */
	public void setLastTile(Tile t) {
		lastTile = t;
	}
	
	/**
	 * Utolsó mezõ gettere
	 * 
	 * @return lastTile - utolsó mezõ melyen az entitás volt
	 */
	public Tile getLastTile() {
		return lastTile;
	}
	
	/**
	 * Megváltoztatja az entitás pozícióját
	 * 
	 * Megváltoztatja az entitás pozícióját a paraméterként kapott érték szerint a paraméterként kapott irányba
	 * 
	 * @param axis - tengely melyen a változás történik
	 * @param number - az eltolás mértéke
	 */
	public void movePosition(char axis, int number) {
		if (axis == 'x')
			position.setX(position.getX() + number);
		if (axis == 'y')
			position.setY(position.getY() + number);
	}
	
	/**
	 * A jelenlegi irány gettere
	 * 
	 * @return direction - jelenlegi irány
	 */
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * Megfordítja az entitást az ellenkezõ irányba
	 */
	public void turnAround() {
		if (direction.equals(Direction.Up) && lastTile != null && lastTile.getNext(Direction.Down) != null)
		{
			direction = Direction.Down;
			return;
		}
		if (direction.equals(Direction.Down) && lastTile != null && lastTile.getNext(Direction.Up) != null)
		{
			direction = Direction.Up;
			return;
		}
		if (direction.equals(Direction.Left) && lastTile != null && lastTile.getNext(Direction.Right) != null)
		{
			direction = Direction.Right;
			return;
		}
		if (direction.equals(Direction.Right) && lastTile != null && lastTile.getNext(Direction.Left) != null)
		{
			direction = Direction.Left;
			return;
		}
	}
	
	/**
	 * Elmozdítja az entitást a paraméterként kapott érték és a jelenlegi irány szerint
	 * 
	 * @param i - elmozdulás mértéke
	 */
	public void move(int i) {
		if (direction.equals(Direction.Up)) {
			movePosition('y', -i);
		}
		if (direction.equals(Direction.Down)) {
			movePosition('y', i);
		}
		if (direction.equals(Direction.Left)) {
			movePosition('x', -i);
		}
		if (direction.equals(Direction.Right)) {
			movePosition('x', i);
		}
	}
}
