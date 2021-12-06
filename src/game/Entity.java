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
	 * Visszaadja az ir�nynak megfelel� k�pet
	 * 
	 * @return Image - entit�s k�pe
	 */
	public Image getImage() {
		return images.get(direction);
	}
	
	/**
	 * Hozz�ad egy k�pet az entit�shoz ir�nynak megfel�en
	 * 
	 * @param i - hozz�adni k�v�nt k�p
	 * @param d - ir�ny melyre a k�p meg fog jelenni
	 */
	public void setImage(Image i, Direction d) {
		images.put(d, i);
	}
	
	/**
	 * Jelenlegi poz�ci� gettere
	 * 
	 * @return position - jelenlegi poz�ci�
	 */
	public Position getPosition() {
		return position;
	}
	
	/**
	 * Jelenlegi poz�ci� settere
	 * 
	 * @param p - jelenlegi poz�ci�
	 */
	public void setPosition(Position p) {
		position.setX(p.getX());
		position.setY(p.getY());
	}
	
	/**
	 * Utols� mez� settere
	 * 
	 * @param t - utols� mez� melyen az entit�s volt
	 */
	public void setLastTile(Tile t) {
		lastTile = t;
	}
	
	/**
	 * Utols� mez� gettere
	 * 
	 * @return lastTile - utols� mez� melyen az entit�s volt
	 */
	public Tile getLastTile() {
		return lastTile;
	}
	
	/**
	 * Megv�ltoztatja az entit�s poz�ci�j�t
	 * 
	 * Megv�ltoztatja az entit�s poz�ci�j�t a param�terk�nt kapott �rt�k szerint a param�terk�nt kapott ir�nyba
	 * 
	 * @param axis - tengely melyen a v�ltoz�s t�rt�nik
	 * @param number - az eltol�s m�rt�ke
	 */
	public void movePosition(char axis, int number) {
		if (axis == 'x')
			position.setX(position.getX() + number);
		if (axis == 'y')
			position.setY(position.getY() + number);
	}
	
	/**
	 * A jelenlegi ir�ny gettere
	 * 
	 * @return direction - jelenlegi ir�ny
	 */
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * Megford�tja az entit�st az ellenkez� ir�nyba
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
	 * Elmozd�tja az entit�st a param�terk�nt kapott �rt�k �s a jelenlegi ir�ny szerint
	 * 
	 * @param i - elmozdul�s m�rt�ke
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
