/**
 * Based on The Cherno's tutorial on YouTube
 *
 */
package com.mime.nothinglefttolose;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.mime.nothinglefttolose.graphics.Render;
import com.mime.nothinglefttolose.graphics.Screen;

public class Display extends Canvas implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Nothing Left to Lose Pre-Alpha 0.045";

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Render render;
	private boolean running = false;
	private int[] pixels;

	public Display() {
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this); // fix from Pre-Alpha 0.03, needed to pass this to Thread()
		thread.start();

		System.out.println("thread.start() working!");
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
		while (running) {
			tick();
			render();
		}

	}

	private void tick() {

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3D game
			return;
		}

		screen.render();

		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.pack();
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // stop program when close button  on window pressed
		frame.setLocationRelativeTo(null);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);

		System.out.println("Running...");

		game.start();

	}

}
