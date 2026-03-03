package br.com.strand.minesfield.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Field {
	private boolean opened = false;
	private boolean undermined = false;
	private boolean marked = false;
	private final int line;
	private final int column;
	private List<Field> neighbors = new ArrayList<>();
	private Set<FieldObserver> observers = new LinkedHashSet<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	public void addObserver(FieldObserver observer) {
		observers.add(observer);
	}
	
	public int hashCode() {
		return Objects.hash(column, line, marked, neighbors, opened, undermined);
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		return column == other.column && line == other.line && marked == other.marked
				&& Objects.equals(neighbors, other.neighbors) && opened == other.opened
				&& undermined == other.undermined;
	}
	
	
	boolean addNeighbor (Field neighbor) {
		boolean lineDifferent = this.line != neighbor.line;
		boolean columnDifferent = this.column != neighbor.column;
		boolean diagonal = lineDifferent && columnDifferent;
		
		int deltaLine = Math.abs(this.line - neighbor.line);
		int deltaColumn = Math.abs(this.column - neighbor.column);
		int deltaGeral = deltaLine + deltaColumn;
		
		if(deltaGeral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if(deltaGeral == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean open() {
		if(!opened && !marked) {
			if(undermined) {
				notifyObservers(FieldEvent.EXPLODE);
				return true;
			}
			
			setOpened(true);
			
			if(safeNeighborhood()) {
				neighbors.forEach(n -> n.open());
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public void toggleMarked() {
		if(!opened) {
			marked = !marked;
			if(marked) {
				notifyObservers(FieldEvent.MARK);
			} else {
				notifyObservers(FieldEvent.UNMARK);
			}
		}
	}
	
	void undermine() {
		this.undermined = true;
	}
	
	public boolean safeNeighborhood() {
		return neighbors.stream().noneMatch(n -> n.undermined);
	}
	
	boolean goalAchieved() {
		boolean revealed = !undermined && opened;
		boolean secure = undermined && marked;
		return revealed || secure;
	}
	
	public int minesInNeighborhood() {
		return (int) neighbors.stream().filter(n -> n.undermined).count();
	}
	
	void restart() {
		opened = false;
		undermined = false;
		marked = false;
		notifyObservers(FieldEvent.RESTART);
	}

	private void notifyObservers(FieldEvent event) {
		observers.stream().forEach(o -> o.eventOccurred(this, event));
	}
	
	// getters and setters
	public boolean isMarked() {
		return this.marked;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
		if (opened) {
			notifyObservers(FieldEvent.OPEN);
		}
	}

	public boolean isOpened() {
		return this.opened;
	}
	
	public boolean isClosed() {
		return !isOpened();
	}
	
	public boolean isUndermined() {
		return undermined;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public List<Field> getNeighbors() {
		return neighbors;
	}
	
	
	
}
