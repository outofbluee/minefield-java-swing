package br.com.strand.minesfield;

import br.com.strand.minesfield.model.Board;
import br.com.strand.minesfield.view.ConsoleBoard;

public class App {
	public static void main(String[] args) {
		Board board = new Board(6, 6, 6);
		new ConsoleBoard(board);
	}
}
