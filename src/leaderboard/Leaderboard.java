package leaderboard;


import javax.swing.*;
import javax.swing.table.TableRowSorter;

import menu.MainMenu;

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

	/**
	 * Létrehozza a grafikus komponenseket
	 */
    private void initComponents() {
        this.setLayout(new BorderLayout());
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
        
        
        try {
            data = new PlayerData();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
            data.setPlayers((List<Player>)ois.readObject());

            ois.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
						oos.writeObject(data.getPlayers());
						oos.close();
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			});

        setMinimumSize(new Dimension(700, 600));
        initComponents();
    }


    /**
     * Megjeleníti a ranglistát
     */
    public void openLeaderboard() {
        Leaderboard sf = new Leaderboard();
        setLocationRelativeTo(null);
        pack();
        sf.setVisible(true);
    }
    



}

