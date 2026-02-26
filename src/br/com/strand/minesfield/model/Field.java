package br.com.strand.minesfield.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.strand.minesfield.exception.ExplosionException;

public class Field {
	private boolean opened = false;
	private boolean undermined = false;
	private boolean marked = false;
	// número de bombas ao redor
	private final int line;
	private final int column;
	private List<Field> neighbors = new ArrayList<>();
	
	Field(int line, int column) {
		this.line = line;
		this.column = column;
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
	
	boolean open() {
		if(!opened && !marked) {
			this.opened = true;
			
			if(undermined) {
				throw new ExplosionException();
			}
			
			if(safeNeighborhood()) {
				neighbors.forEach(n -> n.open());
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	void toggleMarked() {
		if(!opened) {
			marked = !marked;
		}
	}
	
	void undermine() {
		this.undermined = true;
	}
	
	boolean safeNeighborhood() {
		return neighbors.stream().noneMatch(n -> n.undermined);
	}
	
	// outros métodos
	boolean goalAchieved() {
		boolean revealed = !undermined && opened;
		boolean secure = undermined && marked;
		return revealed || secure;
	}
	
	long minesInNeighborhood() {
		return neighbors.stream().filter(n -> n.undermined).count();
	}
	
	void restart() {
		opened = false;
		undermined = false;
		marked = false;
	}

	public String toString() {
		if(marked) {
			return "x";
		} else if(opened && undermined) {
			return "*";
		} else if(opened && minesInNeighborhood() > 0) {
			return Long.toString(minesInNeighborhood());
		} else if(opened) {
			return " ";
		} else {
			return "?";
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, line, marked, neighbors, opened, undermined);
	}

	@Override
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
	
	// getters and setters
	public boolean isMarked() {
		return this.marked;
	}
	
	void setOpened(boolean opened) {
		this.opened = opened;
	}

	public boolean isOpened() {
		return this.opened;
	}
	
	public boolean isClosed() {
		return !isOpened();
	}
	
	boolean isUndermined() {
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
