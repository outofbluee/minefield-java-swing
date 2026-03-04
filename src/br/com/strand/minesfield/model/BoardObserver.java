package br.com.strand.minesfield.model;

public interface BoardObserver {
	public void eventOccurred(BoardEvent event);
}
