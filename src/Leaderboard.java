

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;



public class Leaderboard extends JFrame {
   


	private static final long serialVersionUID = 1L;
	private PlayerData data;

    private void initComponents() {
        this.setLayout(new BorderLayout());
		//táblázat
		JTable jt=new JTable(data);
		jt.setFillsViewportHeight(rootPaneCheckingEnabled);
		jt.setRowSorter(new TableRowSorter<PlayerData>(data));
		

		this.add(new JScrollPane(jt),BorderLayout.CENTER);
		
		JPanel adderPanel=new JPanel(new FlowLayout());
	

		this.add(adderPanel,BorderLayout.SOUTH);
    }

    @SuppressWarnings("unchecked")
    public Leaderboard() {
        super("Leaderboard");
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	dispose();
				MainMenu mainmenu = new MainMenu();
				mainmenu.f.setVisible(true);
            }
        });
        
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        try {
            data = new PlayerData();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
            data.players = (List<Player>)ois.readObject();

            ois.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
						oos.writeObject(data.players);
						oos.close();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			});

        setMinimumSize(new Dimension(700, 600));
        initComponents();
    }


    
    public void openLeaderboard() {
        // Megjelenítjük az ablakot
        Leaderboard sf = new Leaderboard();
        setLocationRelativeTo(null);
        pack();
        sf.setVisible(true);
    }
    



}

