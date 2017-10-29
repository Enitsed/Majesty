package majestyUtility;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

public class MEmulator extends JFrame {
	int width = 1024;
	int height = 1200;
	
	
	public MEmulator() {
		
		setBackground(Color.BLACK);
		setTitle("Majesty");
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	public static void main(String[] args) {
		new MEmulator();
	}

}
