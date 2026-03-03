package br.com.strand.minesfield.model;

public class ResultEvent {
	private final boolean win;

	public ResultEvent(boolean win) {
		super();
		this.win = win;
	}

	public boolean isWin() {
		return win;
	}
}
