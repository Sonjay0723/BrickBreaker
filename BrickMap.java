package brickBreaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BrickMap {
	public boolean bricks[][];
	public int row, col;
	public int brickWidth, brickHeight;
	
	public BrickMap(int x, int y) {
		bricks = new boolean[x][y];
		row = x;
		col = y;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				bricks[i][j] = true;
			}
		}
		
		brickWidth = 275 / x;
		brickHeight = 500 / y;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				if (bricks[i][j]) {
					if ((i + j) % 2 == 0)
						g.setColor(Color.RED);
					else
						g.setColor(Color.WHITE);
				}
				else
					g.setColor(Color.BLACK);
				
				g.fillRect(j * brickWidth + 75, i * brickHeight + 75, brickWidth, brickHeight);
			}
		}
	}
	
	public void removeBrick(int i, int j) {
		bricks[i][j] = false;
	}

	public Rectangle brickMapRect() {
		return new Rectangle(75, 75, col * brickWidth, row * brickHeight);
	}
	
	public Rectangle brickRect(int i, int j) {
		return new Rectangle(j * brickWidth + 75, i * brickHeight + 75, brickWidth, brickHeight);
	}
}

