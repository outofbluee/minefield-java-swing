package br.com.strand.minesfield.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.strand.minesfield.exception.ExitException;
import br.com.strand.minesfield.exception.ExplosionException;
import br.com.strand.minesfield.model.Board;

public class ConsoleBoard {
	private Board board;
	private Scanner input = new Scanner(System.in);
	
	public ConsoleBoard(Board board) {
		this.board = board;
		
		runGame();
	}

	private void runGame() {
		try {
			boolean running = true;
			
			while (running) {
				loopOfGame();
				
				System.out.println("New match? (Y/n) ");
				String response = input.nextLine();
				if("n".equalsIgnoreCase(response)) {
					running = false;
				} else {
					board.restart();
				}
			}
		} catch (ExitException e) {
			System.out.println("Exiting the game.");
		} finally {
			input.close();
		}
	}

	private void loopOfGame() {
		try {
			while(!board.goalAchieved()) {
				System.out.println(board.toString());
				
				String typed = captureEnteredValue("Type (x,y): ");
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
				int x = xy.next();
				int y = xy.next();
				
				typed = captureEnteredValue("1 - Open or 2 - (Un)Mark: ");
				if("1".equals(typed)) {
					board.openField(x, y);
				} else if("2".equals(typed)) {
					board.toggleMarkedField(x, y);
				}
			}
			System.out.println(board.toString());
			System.out.println("You win. Congrats!");
		} catch (ExplosionException e) {
			System.out.println(board.toString());
			System.out.println("You lost. Try again!");
		}
	}
	
	private String captureEnteredValue(String text) {
		System.out.print(text);
		String typed = input.nextLine();
		if ("exit".equalsIgnoreCase(typed)) {
			throw new ExitException();
		}
		
		return typed;
	}
}
