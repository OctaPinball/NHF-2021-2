import java.awt.*;
import javax.swing.*;


public class Entity {
	protected Position position;
	protected Game.Direction direction = Game.Direction.Left;
	protected Tile lastTile = null;
	private double speed;
	private Image texture;
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position p) {
		position.setX(p.getX());
		position.setY(p.getY());
	}
	
	public void setLastTile(Tile t) {
		lastTile = t;
	}
	
	public Tile getLastTile() {
		return lastTile;
	}
	
	public void movePosition(char axis, int number) {
		if (axis == 'x')
			position.setX(position.getX() + number);
		if (axis == 'y')
			position.setY(position.getY() + number);
	}
	
	public Game.Direction getDirection(){
		return direction;
	}
	
	public void turnAround() {
		if (direction.equals(Game.Direction.Up) && lastTile != null && lastTile.getNext(Game.Direction.Down) != null)
			direction = Game.Direction.Down;
		if (direction.equals(Game.Direction.Down) && lastTile != null && lastTile.getNext(Game.Direction.Up) != null)
			direction = Game.Direction.Up;
		if (direction.equals(Game.Direction.Left) && lastTile != null && lastTile.getNext(Game.Direction.Right) != null)
			direction = Game.Direction.Right;
		if (direction.equals(Game.Direction.Right) && lastTile != null && lastTile.getNext(Game.Direction.Left) != null)
			direction = Game.Direction.Left;
	}
	
	public void move(int i) {
		if (direction.equals(Game.Direction.Up)) {
			movePosition('y', -i);
		}
		if (direction.equals(Game.Direction.Down)) {
			movePosition('y', i);
		}
		if (direction.equals(Game.Direction.Left)) {
			movePosition('x', -i);
		}
		if (direction.equals(Game.Direction.Right)) {
			movePosition('x', i);
		}
	}
}
