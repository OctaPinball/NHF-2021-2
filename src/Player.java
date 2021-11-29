import java.io.Serializable;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int score;
	
	Player(String n, int s){
		name = n;
		score = s;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
