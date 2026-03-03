package br.com.strand.minesfield.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements FieldObserver {
	private final int lines;
	private final int columns;
	private final int undermines;
	
	private final List<Field> fields = new ArrayList<>();
	private final Set<Consumer<ResultEvent>> observers = new LinkedHashSet<>();

	public Board(int lines, int columns, int undermines) {
		this.lines = lines;
		this.columns = columns;
		this.undermines = undermines;
		
		generateFields();
		associateNeighbors();
		drawMines();
	}
	
	public void addObserver(Consumer<ResultEvent> obsever) {
		observers.add(obsever);
	}
	
	public void forEachField(Consumer<Field> function) {
		fields.forEach(function);
	}
	
	public void openField(int line, int column) {
		Predicate<Field> findField = f -> f.getLine() == line && f.getColumn() == column;
		
			fields.stream()
				.filter(findField)
				.findFirst()
				.ifPresent(f -> f.open());
	}
	
	public void toggleMarkedField(int line, int column) {
		Predicate<Field> findField = f -> f.getLine() == line && f.getColumn() == column;
		fields.stream()
			.filter(findField)
			.findFirst()
			.ifPresent(f -> f.toggleMarked());
	}
	
	private void notifyObservers(boolean result) {
		observers.stream().forEach(o -> o.accept(new ResultEvent(result)));
	}
	
	private void generateFields() {
		for (int line = 0; line < lines; line++) {
			for (int column = 0; column < columns; column++) {
				Field field = new Field(line, column);
				field.addObserver(this);
				fields.add(field);
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
	
	public boolean goalAchieved() {
		return fields.stream().allMatch(f -> f.goalAchieved());
	}
	
	public void restart() {
//		fields.stream().forEach(Field::restart);
		fields.stream().forEach(f -> f.restart());
		drawMines();
	}
	
	public List<Field> getFields() {
		return fields;
	}

	public int getUndermines() {
		return undermines;
	}

	public void eventOccurred(Field field, FieldEvent event) {
		if (event == FieldEvent.EXPLODE) {
			showMines();
			notifyObservers(false);
		} else if (goalAchieved()) {
			notifyObservers(true);
		}
	}
	
	private void showMines() {
		fields.stream()
		.filter(c -> c.isUndermined())
		.filter(c -> !c.isMarked())
		.forEach(f -> f.setOpened(true));
	}

	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}
	
	
}
