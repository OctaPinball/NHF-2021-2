package leaderboard;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


public class PlayerData extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	private List<Player> players = new ArrayList<>();

	/**
	 * Visszadja a sorok sz�m�t
	 */
	@Override
	public int getRowCount() {
		return getPlayers().size();
	}

	/**
	 * Visszadaja az oszlopok sz�m�t (2)
	 */
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	/**
	 * Visszadaja a t�bl�zat egy elem�t a megfelel� sorban �s oszlopban
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
	 * Az ir�ny�t�shoz egy Listener-t ad
	 */
	@Override
	public void addTableModelListener(TableModelListener tl) {
		super.addTableModelListener(tl);
	}
	
	/**
	 * Visszadja az oszlop nev�t
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
	 * Visszadja az oszlop oszt�ly�t
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
	 * Hozz�ad egy j�t�kost a t�bl�zathoz
	 * 
	 * @param name - j�t�kos neve
	 * @param score - j�t�kos pontsz�ma
	 */
	public void addPlayer(String name, int score)
	{
		getPlayers().add(new Player(name, score));
		this.fireTableDataChanged();
	}

	/**
	 * Visszadja a j�t�kosokat tartalmaz� list�t
	 * 
	 * @return List - j�t�kosokat tartalmaz� lista
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Megadja a j�t�kosokat tartalmaz� list�t
	 * 
	 * @param players - j�t�kosokat tartalmaz� lista
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
