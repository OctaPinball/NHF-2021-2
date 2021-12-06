package game;

public class Position {
	private int x;
	private int y;
	public Position(int x_in, int y_in) {
		setX(x_in);
		y = y_in;
	}
	
	/**
	 * Az x koordin�ta gettere
	 * 
	 * @return x - x koordin�ta
	 */
	public int getX() {
		return x;
	}
	/**
	 * Az x koordin�ta settere
	 * 
	 * @param x_in - x koordin�ta
	 */
	public void setX(int x_in) {
		this.x = x_in;
	}
	/**
	 * Az y koordin�ta gettere
	 * 
	 * @return y - y koordin�ta
	 */
	public int getY() {
		return y;
	}
	/**
	 * Az y koordin�ta settere
	 * 
	 * @param y_in - y koordin�ta
	 */
	public void setY(int y_in) {
		this.y = y_in;
	}
	
	/**
	 * Visszadja k�t Position k�z�tti t�vols�got
	 * 
	 * @param p - m�sik Position, amelyt�l a t�vols�got kell sz�molni
	 * @return double - k�t Position t�vols�ga
	 */
	public double measureDistance(Position p) {
		if(p == null)
			return 0;
		return (Math.sqrt(Math.pow(x-p.getX(), 2) + Math.pow(y-p.getY(), 2)));
	}
	
	/**
	 * Position settere. A param�ter Position koordin�t�it veszi �t
	 * 
	 * @param p - Position
	 */
	public void setPosition(Position p) {
		x = p.getX();
		y = p.getY();
	}
	
	/**
	 * Megvizsg�lja, hogy k�t Position egyenl�-e
	 * 
	 * @param p - Position amelyel esetleg egyenl�
	 * @return boolean - igaz, ha egyenl�ek, hamis ha nen
	 */
	public boolean equals(Position p) {
		return (x == p.getX() && y == p.getY());
	}
	
	/**
	 * Migvizsg�lja, hogy k�t Position egyenl�-e x �s y beli eltol�st is sz�molva
	 * 
	 * @param p - Position
	 * @param x_offset - x tengelybeli eltol�s
	 * @param y_offset - y tengelybeli eltol�s
	 * @return boolean - igaz, ha egyenl�ek, hamis ha nem
	 */
	public boolean equals(Position p, int x_offset, int y_offset) {
		if (x + x_offset == p.getX() && y + y_offset == p.getY())
			return true;
		return false;
	}
	
	/**
	 * K�z�ppontos t�kr�z�st v�gez a param�terk�nt kapott Position-re, majd a kapott Position-nel visszat�r
	 * 
	 * @param o - Position, amelyre t�kr�z�nk
	 * @return Position - amely a t�kr�z�s eredm�nye
	 */
	public Position pointMirror(Position o) {
		return (new Position(-this.getX() + 2*o.getX(), -this.getY() + 2*o.getY()));
	}
	
	/**
	 * Visszaadja az ir�ny szerinti Position el�tti offsettel eltolt Position-t. Belesz�m�tja az eredeti j�t�k szerinti hib�s eltol�st felfel� ir�nyn�l.
	 * 
	 * @param d - Direction (ir�ny)
	 * @param offset - eltol�s m�rt�ke
	 * @return Position - a sz�m�t�s Position eredm�nye
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
