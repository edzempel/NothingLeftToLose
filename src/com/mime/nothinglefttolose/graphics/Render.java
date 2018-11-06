package com.mime.nothinglefttolose.graphics;

import com.mime.nothinglefttolose.Display;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int xOffset, int yOffset) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= height) {
				continue; // skip if out of vertical bounds
			}
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= width) {
					continue;
				}

				// only render what is visible
				int alpha = render.pixels[x + y * render.width];
				if (alpha > 0) {
					pixels[xPix + yPix * width] = render.pixels[x + y * render.width];
					// System.out.println("x: " + x + " y: " + y);
				}
			}
		}
	}

}
