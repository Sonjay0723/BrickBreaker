package brickBreaker;

import javax.swing.JFrame;

public class Main extends JFrame {
	public static void main(String[] args) {
		int row = 3, col = 7;
		JFrame obj = new JFrame();
		Game game = new Game(row, col);
		
		obj.setBounds(10, 10, 800, 600);
		obj.setTitle("Brick Breaker");
		obj.setVisible(true);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		obj.add(game);
	}
}
