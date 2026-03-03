package br.com.strand.minesfield.view;

import javax.swing.JFrame;

import br.com.strand.minesfield.model.Board;

@SuppressWarnings("serial")
public class MainScreen extends JFrame{
	
	public MainScreen() {
		Board board = new Board(16, 30, 50);
		
		add(new BoardPanel(board));
		setTitle("Mines Field");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainScreen();
	}
}
