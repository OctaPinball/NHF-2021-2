package game;

public class Position {
	private int x;
	private int y;
	public Position(int x_in, int y_in) {
		setX(x_in);
		y = y_in;
	}
	
	/**
	 * Az x koordináta gettere
	 * 
	 * @return x - x koordináta
	 */
	public int getX() {
		return x;
	}
	/**
	 * Az x koordináta settere
	 * 
	 * @param x_in - x koordináta
	 */
	public void setX(int x_in) {
		this.x = x_in;
	}
	/**
	 * Az y koordináta gettere
	 * 
	 * @return y - y koordináta
	 */
	public int getY() {
		return y;
	}
	/**
	 * Az y koordináta settere
	 * 
	 * @param y_in - y koordináta
	 */
	public void setY(int y_in) {
		this.y = y_in;
	}
	
	/**
	 * Visszadja két Position közötti távolságot
	 * 
	 * @param p - másik Position, amelytõl a távolságot kell számolni
	 * @return double - két Position távolsága
	 */
	public double measureDistance(Position p) {
		if(p == null)
			return 0;
		return (Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y-p.getY(), 2)));
	}
	
	/**
	 * Position settere. A paraméter Position koordinátáit veszi át
	 * 
	 * @param p - Position
	 */
	public void setPosition(Position p) {
		x = p.getX();
		y = p.getY();
	}
	
	/**
	 * Megvizsgálja, hogy két Position egyenlõ-e
	 * 
	 * @param p - Position amelyel esetleg egyenlõ
	 * @return boolean - igaz, ha egyenlõek, hamis ha nen
	 */
	public boolean equals(Position p) {
		return (x == p.getX() && y == p.getY());
	}
	
	/**
	 * Migvizsgálja, hogy két Position egyenlõ-e x és y beli eltolást is számolva
	 * 
	 * @param p - Position
	 * @param x_offset - x tengelybeli eltolás
	 * @param y_offset - y tengelybeli eltolás
	 * @return boolean - igaz, ha egyenlõek, hamis ha nem
	 */
	public boolean equals(Position p, int x_offset, int y_offset) {
		if (x + x_offset == p.getX() && y + y_offset == p.getY())
			return true;
		return false;
	}
	
	/**
	 * Középpontos tükrözést végez a paraméterként kapott Position-re, majd a kapott Position-nel visszatér
	 * 
	 * @param o - Position, amelyre tükrözünk
	 * @return Position - amely a tükrözés eredménye
	 */
	public Position pointMirror(Position o) {
		return (new Position(-this.getX() + 2*o.getX(), -this.getY() + 2*o.getY()));
	}
	
	/**
	 * Visszaadja az irány szerinti Position elõtti offsettel eltolt Position-t. Beleszámítja az eredeti játék szerinti hibás eltolást felfelé iránynál.
	 * 
	 * @param d - Direction (irány)
	 * @param offset - eltolás mértéke
	 * @return Position - a számítás Position eredménye
	 */
	public Position wrongPositionOffset(Direction d, int offset) {
		if(d.equals(Direction.Up))
			return (new Position(this.getX() - offset, this.getY() - offset));
		if(d.equals(Direction.Down))
			return (new Position(this.getX(), this.getY() + offset));
		if(d.equals(Direction.Left))
			return (new Position(this.getX() - offset, this.getY()));
		if(d.equals(Direction.Right))
			return (new Position(this.getX() + offset, this.getY()));
		return null;
	}
}
