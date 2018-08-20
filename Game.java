package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {
	private boolean play = false, gameOver = false, win = false;
	private int score = 0;
	private int row, col;
	private int bricks;
	private int bricksHit = 0;
	
	private Random rand = new Random();
	
	private int slider = 350, movement = 0;
	
	private Timer tmr = new Timer(5, this);
	
	private int ballPosX, ballPosY;
	private double ballSpdX, ballSpdY;
	
	private BrickMap brickMap;
	
	public Game(int x, int y) {
		row = x;
		col = y;
		brickMap = new BrickMap(row, col);
		bricks = row * col;
		ballPosX = rand.nextInt(30) + 120;
		ballPosY = rand.nextInt(30) + 400;
		ballSpdX = rand.nextInt(2) + 2;
		ballSpdY = -(5 - ballSpdX);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		tmr.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		//borders
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 10, 600);
		g.fillRect(0, 0, 800, 10);
		g.fillRect(785, 0, 10, 600);
		g.fillRect(0, 540, 800, 10);
		//score
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		g.drawString("Score: " + score, 650, 50);
		//bricks
		brickMap.draw((Graphics2D)g);
		//slider
		g.setColor(Color.GREEN);
		g.fillRect(slider, 525, 100, 10);
		//ball
		g.setColor(Color.BLUE);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		//game over messages
		if (gameOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.BOLD, 100));
			g.drawString("GAME OVER", 85, 400);
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g.drawString("Press 'Enter' to play again", 170, 470);
			tmr.stop();
		}
		if (win) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.BOLD, 70));
			g.drawString("CONGRATULATIONS!", 25, 400);
			g.setFont(new Font("TimesRoman", Font.BOLD, 40));
			g.drawString("Press 'Enter' to play again", 170, 470);
			tmr.stop();
		}
		
		g.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(slider >= 690)
				movement = 0;
			else {
				movement = 4;
				play = true;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(slider <= 10)
				movement = 0;
			else {
				movement = -4;
				play = true;
			}
		}
		
		if ((gameOver || win) && e.getKeyCode() == KeyEvent.VK_ENTER) {
			slider = 350;
			ballPosX = rand.nextInt(30) + 120;
			ballPosY = rand.nextInt(30) + 400;
			ballSpdX = rand.nextInt(2) + 2;
			ballSpdY = -(5 - ballSpdX);
			gameOver = false;
			win = false;
			brickMap = new BrickMap(row, col);
			bricks = row * col;
			score = 0;
			tmr.start();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		movement = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Rectangles for objects
		Rectangle rectBall = new Rectangle(ballPosX, ballPosY, 20, 20);
		Rectangle rectSlider = new Rectangle(slider, 525, 100, 10);
		Rectangle rectBrick;
		
		if (rectBall.intersects(rectSlider)) {
			if (rectBall.y + 17 <= 525)
				ballSpdY = -ballSpdY;
			else
				ballSpdX = -ballSpdX;
		}
		
		if (slider <= 10)
			slider = 10;
		if (slider >= 685)
			slider = 685;
		
		if (ballPosX <= 10)
			ballSpdX = -ballSpdX;
		if (ballPosX >= 765)
			ballSpdX = -ballSpdX;
		if (ballPosY <= 10)
			ballSpdY = -ballSpdY;
		
		for (int i = 0; i < brickMap.bricks.length; i++) {
			for (int j = 0; j < brickMap.bricks[0].length; j++) {
				rectBrick = new Rectangle(j * brickMap.brickWidth + 75, i * brickMap.brickHeight + 75, brickMap.brickWidth, brickMap.brickHeight);
				if (rectBall.intersects(rectBrick) && brickMap.bricks[i][j]) {
					brickMap.removeBrick(i, j);
					bricksHit++;
					bricks--;
					score += 10;
					
					if (ballPosX + 19 <= brickMap.brickRect(i, j).x || ballPosX + 1 >= brickMap.brickRect(i, j).x + brickMap.brickRect(i, j).width)
						ballSpdX = -ballSpdX;
					else
						ballSpdY = -ballSpdY;
					
					System.out.println("hi");
				}
			}
		}
		
		if (bricksHit > 1) {
			ballSpdX = -ballSpdX;
			ballSpdY = -ballSpdY;
		}
		
		bricksHit = 0;
		
		if (ballPosY >= 765) {
			gameOver = true;
			endGame();
		}
		
		if (bricks <= 0) {
			win = true;
			endGame();
		}
		
		//movement
		slider += movement;
		if (play) {
			ballPosX += ballSpdX;
			ballPosY += ballSpdY;
		}
		
		System.out.println("hello");
		repaint();
	}
	
	public void endGame() {
		ballSpdX = 0;
		ballSpdY = 0;
		movement = 0;
		play = false;
	}
}