package br.com.strand.minesfield.model;

public class BoardEvent {
	private final boolean win;

	public BoardEvent(boolean win) {
		super();
		this.win = win;
	}

	public boolean isWin() {
		return win;
	}
}
