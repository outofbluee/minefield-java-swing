package br.com.strand.minesfield.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldTest {
	
	private Field field;
	
	@BeforeEach
	void initializeField() {
		field = new Field(3, 3);
	}
	
	@Test
	void testNeighborDistanceOf1Left() {
		Field neighbor = new Field(3, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf1Right() {
		Field neighbor = new Field(3, 4);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf1Up() {
		Field neighbor = new Field(2, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf1Dowm() {
		Field neighbor = new Field(4, 3);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf2UpLeft() {
		Field neighbor = new Field(2, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf2UpRight() {
		Field neighbor = new Field(2, 4);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf2DowmLeft() {
		Field neighbor = new Field(4, 2);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNeighborDistanceOf2DowmRight() {
		Field neighbor = new Field(4, 4);
		boolean result = field.addNeighbor(neighbor);
		assertTrue(result);
	}
	
	@Test
	void testNotNeighborDistanceOf2Up() {
		Field neighbor = new Field(1, 3);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testNotNeighborDistanceOf2Dowm() {
		Field neighbor = new Field(5, 3);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testNotNeighbor() {
		Field neighbor = new Field(1, 1);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testNeighborSamePosition() {
		Field neighbor = new Field(3, 3);
		boolean result = field.addNeighbor(neighbor);
		assertFalse(result);
	}
	
	@Test
	void testDefaultValueAttributeMarked() {
		assertFalse(field.isMarked());
	}
	
	@Test
	void testToggleMarked() {
		field.toggleMarked();
		assertTrue(field.isMarked());
	}
	
	@Test
	void testToggleMarkedTwoCalls() {
		field.toggleMarked();
		field.toggleMarked();
		assertFalse(field.isMarked());
	}
	
	@Test
	void testOpenNotUnderminedNotMarked() {
		assertTrue(field.open());
	}
	
	@Test
	void testOpenNotUnderminedMarked() {
		field.toggleMarked();
		assertFalse(field.open());
	}
	
	@Test
	void testOpenUnderminedMarked() {
		field.undermine();
		field.toggleMarked();
		assertFalse(field.open());
	}
	
	@Test
	void testWithNeighbors1() {
		Field field11 = new Field(1, 1);
		Field field22 = new Field(2, 2);
		field22.addNeighbor(field11);
		field.addNeighbor(field22);
		
		field.open();
		
		assertTrue(field22.isOpened() && field11.isOpened());
	}
	
	@Test
	void testWithNeighbors2() {
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 2);
		field12.undermine();
		Field field22 = new Field(2, 2);
		field22.addNeighbor(field11);
		field22.addNeighbor(field12);
		field.addNeighbor(field22);
		
		field.open();
		
		assertTrue(field22.isOpened() && field11.isClosed());
	}
	
	@Test
	void testToStringMarked() {
		field.toggleMarked();
		assertEquals(field.toString(), "x");
	}
	
	@Test
	void testToStringOpenedNotMinesInNeighbor() {
		field.open();
		assertEquals(field.toString(), " ");
	}
	
	@Test
	void testToStringOpenedMinesInNeighbor() {
		Field field11 = new Field(1, 1);
		Field field12 = new Field(1, 2);
		field12.undermine();
		Field field22 = new Field(2, 2);
		field22.undermine();
		field22.addNeighbor(field11);
		field22.addNeighbor(field12);
		field.addNeighbor(field22);
		
		field.open();
		assertTrue(field.toString().matches("\\d"));
//		assertEquals(field.toString(), "1");
	}
	
	@Test
	void testToStringClosed() {
		assertEquals(field.toString(), "?");
	}
	
	@Test
	void testRestart() {
		assertTrue(!field.isOpened() && !field.isMarked() && !field.isUndermined());
	}
	
}
