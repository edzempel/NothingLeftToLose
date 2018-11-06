/**
 * Based on The Cherno's tutorial on YouTube
 *
 */
package com.mime.nothinglefttolose;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.mime.nothinglefttolose.graphics.Render;
import com.mime.nothinglefttolose.graphics.Screen;

public class Display extends Canvas implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Nothing Left to Lose Pre-Alpha 0.08";

	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;

	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(WIDTH, HEIGHT);
		game = new Game();
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
		int frames = 0; // amount of frames that are rendered
		double unprocessedSeconds = 0; // amount of seconds that aren't recorded yet
		long previousTime = System.nanoTime(); // 64 bit integer, most precise available system time
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		
		// fps counter
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0; // nano is 1 billion
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					previousTime += 1000;
					frames = 0;	
				}
			}
			if (ticked == true) {
				render();
				frames++;
			}
			render();
			frames++;
		}

	}

	private void tick() {
		game.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3D game
			return;
		}

		screen.render(game);

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
		//frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);

		System.out.println("Running...");

		game.start();

	}

}
