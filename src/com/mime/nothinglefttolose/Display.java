/**
 * Based on The Cherno's tutorial on YouTube
 */
package com.mime.nothinglefttolose;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Display extends Canvas {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Nothing Left to Lose Pre-Alpha 0.01";
	
	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		System.out.println("Running...");
	
	}

}
