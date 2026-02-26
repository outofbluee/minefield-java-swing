package br.com.strand.minesfield.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
	
	private Board board;

	@BeforeEach
	void initializeField() {
		board = new Board(6, 6, 3);
	}
	
	@Test
	void testNumberOfFields() {
		assertEquals(36, board.getFields().size());
	}
	
	@Test
	void testFieldPosition00() {
		List<Integer> expectedPosition = Arrays.asList(new Field(0, 0).getLine(), new Field(0, 0).getColumn());
		List<Integer> realPosition = Arrays.asList(board.getFields().get(0).getLine(), board.getFields().get(0).getColumn());
		
		assertEquals(expectedPosition, realPosition);
	}
	
	@Test
	void testField00NumberOfNeighbor() {
		assertEquals(3, board.getFields().get(0).getNeighbors().size());
	}
	
	@Test
	void testNumberOfFieldsUndermined() {
		long numberOfUndermines = board.getFields().stream().filter(f -> f.isUndermined()).count();
		assertEquals(board.getUndermines(), numberOfUndermines);
	}

}
