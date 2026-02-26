package br.com.strand.minesfield.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.strand.minesfield.exception.ExplosionException;

public class Board {
	private int lines;
	private int columns;
	private int undermines;
	
	private final List<Field> fields = new ArrayList<>();

	public Board(int lines, int columns, int undermines) {
		this.lines = lines;
		this.columns = columns;
		this.undermines = undermines;
		
		generateFields();
		associateNeighbors();
		drawMines();
	}
	
	// abrir campos
	public void openField(int line, int column) {
		Predicate<Field> findField = f -> f.getLine() == line && f.getColumn() == column;
		
		try {
			fields.stream()
				.filter(findField)
				.findFirst()
				.ifPresent(f -> f.open());
		} catch (ExplosionException e) {
			fields.forEach(f -> f.setOpened(true));
			throw e;
		}
	}
	
	// marcar campos
	public void toggleMarkedField(int line, int column) {
		Predicate<Field> findField = f -> f.getLine() == line && f.getColumn() == column;
		fields.stream()
			.filter(findField)
			.findFirst()
			.ifPresent(f -> f.toggleMarked());
	}
	
	private void generateFields() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				fields.add(new Field(line, column));
			}
		}
	}
	
	private void associateNeighbors() {
		for (Field f1 : fields) {
			for (Field f2 : fields) {
				f1.addNeighbor(f2);
			}
		}
	}
	
	private void drawMines() {
		long armedMines = 0;
		Predicate<Field> mined = f -> f.isUndermined();
		
		do {
			int random = (int) (Math.random() * fields.size());
			armedMines = fields.stream().filter(mined).count();
			fields.get(random).undermine();
		} while (armedMines < undermines);
	}
	
	// acabar jogo venceu
	public boolean goalAchieved() {
		return fields.stream().allMatch(f -> f.goalAchieved());
	}
	
	// acabar jogo reiniciar
	public void restart() {
//		fields.stream().forEach(Field::restart);
		fields.stream().forEach(f -> f.restart());
		drawMines();
	}
	
	// acabar jogo perdeu
	
	
	
	// exibir campos
	
	// toString
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int index = 0;
		
		sb.append("  ");
		for (int column = 0; column < columns; column++) {
			sb.append(" ");
			sb.append(column);
			sb.append(" ");
		}
		sb.append("\n");
		
		for (int line = 0; line < lines; line++) {
			sb.append(line + " ");
			for (int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(fields.get(index));
				sb.append(" ");
				index++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	// getters e setters
	public List<Field> getFields() {
		return fields;
	}

	public int getUndermines() {
		return undermines;
	}
}
