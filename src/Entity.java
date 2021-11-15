import java.awt.*;
import javax.swing.*;


public class Entity {
	protected Position position;
	protected Game.Direction direction = Game.Direction.Left;
	private double speed;
	private Image texture;
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position p) {
		position.setX(p.getX());
		position.setY(p.getY());
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
	
	public void move() {
		if (direction.equals(Game.Direction.Up)) {
			movePosition('y', -2);
		}
		if (direction.equals(Game.Direction.Down)) {
			movePosition('y', 2);
		}
		if (direction.equals(Game.Direction.Left)) {
			movePosition('x', -2);
		}
		if (direction.equals(Game.Direction.Right)) {
			movePosition('x', 2);
		}
	}
}
