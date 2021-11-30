
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


public class PlayerData extends AbstractTableModel{


	private static final long serialVersionUID = 1L;
	List<Player> players = new ArrayList<>();

	@Override
	public int getRowCount() {
		return players.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int i, int i1) {
		Player player = players.get(i);
		switch(i1) {
			case 0: return player.getName();
			default: return player.getScore();
		}
	}

	@Override
	public void addTableModelListener(TableModelListener tl) {
		super.addTableModelListener(tl);
	}
	
	@Override
	public String getColumnName(int i)
	{
		switch(i)
		{
			case 0: return "Name";
			default: return "Score";
		}
	}
    
	@Override
	public Class<?> getColumnClass(int i)
	{
		switch(i)
		{
			case 0: return String.class;
			default: return Integer.class;

		}
	}

	@Override
	public boolean isCellEditable(int i, int i1)
	{
		boolean[] b={false,false,true,true};
		return (i1<getColumnCount() && i1>=0)?b[i1]:false;
	}
	
	@Override
	public void setValueAt(Object o, int i, int i1)
	{
		/*
		Player s=players.get(i);
		if(i1>=2 && i1<=3)
		{
			switch(i1)
			{	
				case 2:
					s.setSignature((Boolean)o);
					break;
				default: 
					s.setGrade((Integer)o);
					break;
			}
			players.set(i, s);
			this.fireTableRowsUpdated(i, i);
		}
		*/
	}

	public void addPlayer(String name, int score)
	{
		players.add(new Player(name, score));
		this.fireTableDataChanged();
	}
}
