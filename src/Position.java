
public class Position {
	private int x;
	private int y;
	public Position(int x_in, int y_in) {
		setX(x_in);
		y = y_in;
	}
	public int getX() {
		return x;
	}
	public void setX(int x_in) {
		this.x = x_in;
	}
	public int getY() {
		return y;
	}
	public void setY(int y_in) {
		this.y = y_in;
	}
	public String toString() {
		return "X= " + x + "  Y= " + y;
	}
	public double measureDistance(Position p) {
		return (Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y-p.getY(), 2)));
	}
	public void setPosition(Position p) {
		x = p.getX();
		y = p.getY();
	}
	public boolean equals(Position p) {
		return (x == p.getX() && y == p.getY());
	}
	public boolean equals(Position p, int x_offset, int y_offset) {
		if (x + x_offset == p.getX() && y + y_offset == p.getY())
			return true;
		return false;
	}
	public Position pointMirror(Position o) {
		return (new Position(-this.getX() + 2*o.getX(), -this.getY() + 2*o.getY()));
	}
	public Position wrongPositionOffset(Game.Direction d, int offset) {
		if(d.equals(Game.Direction.Up))
			return (new Position(this.getX() - offset, this.getY() - offset));
		if(d.equals(Game.Direction.Down))
			return (new Position(this.getX(), this.getY() + offset));
		if(d.equals(Game.Direction.Left))
			return (new Position(this.getX() - offset, this.getY()));
		if(d.equals(Game.Direction.Right))
			return (new Position(this.getX() + offset, this.getY()));
		return null;
	}
}
