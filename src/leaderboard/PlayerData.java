package leaderboard;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


public class PlayerData extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private List<Player> players = new ArrayList<>();

	/**
	 * Visszadja a sorok számát
	 */
	@Override
	public int getRowCount() {
		return getPlayers().size();
	}

	/**
	 * Visszadaja az oszlopok számát (2)
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	/**
	 * Visszadaja a táblázat egy elemét a megfelelõ sorban és oszlopban
	 */
	@Override
	public Object getValueAt(int i, int i1) {
		Player player = getPlayers().get(i);
		switch(i1) {
			case 0: return player.getName();
			default: return player.getScore();
		}
	}

	/**
	 * Az irányításhoz egy Listener-t ad
	 */
	@Override
	public void addTableModelListener(TableModelListener tl) {
		super.addTableModelListener(tl);
	}
	
	/**
	 * Visszadja az oszlop nevét
	 */
	@Override
	public String getColumnName(int i)
	{
		switch(i)
		{
			case 0: return "Name";
			default: return "Score";
		}
	}
    
	/**
	 * Visszadja az oszlop osztályát
	 */
	@Override
	public Class<?> getColumnClass(int i)
	{
		switch(i)
		{
			case 0: return String.class;
			default: return Integer.class;

		}
	}


	

	/**
	 * Hozzáad egy játékost a táblázathoz
	 * 
	 * @param name - játékos neve
	 * @param score - játékos pontszáma
	 */
	public void addPlayer(String name, int score)
	{
		getPlayers().add(new Player(name, score));
		this.fireTableDataChanged();
	}

	/**
	 * Visszadja a játékosokat tartalmazó listát
	 * 
	 * @return List - játékosokat tartalmazó lista
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Megadja a játékosokat tartalmazó listát
	 * 
	 * @param players - játékosokat tartalmazó lista
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
