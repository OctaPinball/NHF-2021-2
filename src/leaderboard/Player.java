package leaderboard;
import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	
	Player(String n, int s){
		name = n;
		score = s;
	}
	
	/**
	 * J�t�kos nev�nek gettere
	 * 
	 * @return String - j�t�kos neve
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * J�t�kos nev�nek settere
	 * 
	 * @param name - j�t�kos neve
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * J�t�kos pontsz�m�nak gettere
	 * 
	 * @return int - j�t�kos pontsz�ma
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * J�t�kos pontsz�m�nak settere
	 * 
	 * @param score - j�t�kos pontsz�ma
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
