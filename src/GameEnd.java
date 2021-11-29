import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class GameEnd {
	public class OkButtonActionListener implements ActionListener{
		public OkButtonActionListener() {}
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("enter")) 
			{
				PlayerData data = null;
		        try {
		            data = new PlayerData();
		            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("leaderboard.dat"));
		            data.players = (List<Player>)ois.readObject();

		            ois.close();
		        } catch(Exception ex) {
		            ex.printStackTrace();
		        }
		        data.addPlayer(input.getText(),score);
							try {
								ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("leaderboard.dat"));
								oos.writeObject(data.players);
								oos.close();
							} catch(Exception ex) {
								ex.printStackTrace();
							}
							f.dispose();
							MainMenu mainmenu = new MainMenu();
							mainmenu.f.setVisible(true);

			}
		}
	}

	private static final long serialVersionUID = 1L;
	public JFrame f = new JFrame("PacMan");
	private JPanel p = new JPanel();
	private JButton b_enter = new JButton("ENTER");
	private JTextField input = new JTextField("");
	private JTextField output = new JTextField("GREAT SCORE! ENTER YOUR NAME");
	private int score;


	
	GameEnd(int s){
		score = s;
		f.setSize(400,220);
		f.setResizable(true);
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		b_enter.setActionCommand("enter");
		output.setEditable(false);
		ActionListener al = new OkButtonActionListener();
		b_enter.addActionListener(al);
		p.setLayout(new BorderLayout(50, 50));
		p.add(output, BorderLayout.NORTH);
		p.add(input, BorderLayout.CENTER);
		p.add(b_enter, BorderLayout.SOUTH);
		f.add(p);
		f.setLocationRelativeTo(null);
        f.pack();
        f.setVisible(true);
	}
}
