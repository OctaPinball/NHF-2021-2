import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

	Game panel;
	Controls controls;
	
	MainFrame(){
		controls = new Controls();
		panel = new Game(controls);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
