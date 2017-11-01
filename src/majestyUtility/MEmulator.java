package majestyUtility;

import java.awt.Color;
import java.io.File;

import javax.swing.JFrame;

public class MEmulator extends JFrame {
	int width = 1024;
	int height = 1200;

	public MEmulator() {
		File jpg = new File("//resources//0000.bmp");

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
