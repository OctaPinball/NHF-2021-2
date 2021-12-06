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
	 * Játékos nevének gettere
	 * 
	 * @return String - játékos neve
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Játékos nevének settere
	 * 
	 * @param name - játékos neve
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Játékos pontszámának gettere
	 * 
	 * @return int - játékos pontszáma
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Játékos pontszámának settere
	 * 
	 * @param score - játékos pontszáma
	 */
	public void setScore(int score) {
		this.score = score;
	}
}
