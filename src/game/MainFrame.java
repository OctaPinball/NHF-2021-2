package game;
import javax.swing.*;

import menu.MainMenu;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private Game panel;
	private Controls controls;
	
	public MainFrame(){
		controls = new Controls();
		setPanel(new Game(controls, this));
		
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	dispose();
            	getPanel().stopTimer();
				MainMenu mainmenu = new MainMenu();
				mainmenu.f.setVisible(true);
            }
        });
		

		add(getPanel());
		setTitle("PacMan");
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(controls);
		setVisible(true);
		setFocusable(true);
		requestFocus();
	}

	/**
	 * A panel gettere
	 * 
	 * @return panel
	 */
	public Game getPanel() {
		return panel;
	}

	/**A panel settere
	 * 
	 * @param panel
	 */
	public void setPanel(Game panel) {
		this.panel = panel;
	}
	
}
