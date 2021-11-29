import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	Game panel;
	Controls controls;
	
	MainFrame(){
		controls = new Controls();
		panel = new Game(controls, this);
		
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	dispose();
            	panel.stopTimer();
				MainMenu mainmenu = new MainMenu();
				mainmenu.f.setVisible(true);
            }
        });
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(panel);
		setTitle("PacMan");
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		addKeyListener(controls);
		setVisible(true);
		setFocusable(true);
		requestFocus();
	}
	
}
