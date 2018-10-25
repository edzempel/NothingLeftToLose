/**
 * Based on The Cherno's tutorial on YouTube
 * video 2: https://youtu.be/0zuVHDNYPQU
 */
package com.mime.nothinglefttolose;

import java.awt.Canvas;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Nothing Left to Lose Pre-Alpha 0.02";

	private Thread thread;
	private boolean running = false;

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread();
		thread.start();
		
		System.out.println("Working!");
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join(); // tries to wait for thread to die or throws an exception
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	@Override
	public void run() {
		while(running) {
			
		}

	}

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
		
		game.start();

	}

}
