package com.mime.nothinglefttolose.graphics;

import com.mime.nothinglefttolose.Game;

import java.util.Random;

public class Screen extends Render {

	private Render test;

	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
		    // add empty pixels
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}

	public void render(Game game) {
		// removes previously rendered image
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		// creates a tail to follow the animation
		for (int i = 0; i < 50; i++) {
			// generated values to create sample animation
			//add multiplier to i to reduce the loops required. This makes rendering less intense
			int anim = (int) (Math.sin((game.time + i * 2) % 1000.0 / 100) * 100);
            int anim2 = (int) (Math.cos((game.time + i * 2) * 1000.0 / 100) * 100);
			draw(test, (width - 256) / 2 + anim, (height - 256) / 2 + anim2);
		}
	}

}
