package br.com.strand.minesfield.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.strand.minesfield.model.Board;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	
	public BoardPanel(Board board) {
		setLayout(new GridLayout(board.getLines(), board.getColumns()));
		int total = board.getLines() * board.getColumns();
		
		board.forEachField(c -> add(new FieldButton(c)));
		board.addObserver(e -> {
			SwingUtilities.invokeLater(() -> {
				if(e.isWin()) {
					JOptionPane.showMessageDialog(this, "You win. Congrats!");
				} else {
					JOptionPane.showMessageDialog(this, "You lost. Try again.");
				}
				
				board.restart();
				
			});
		});
	}
	
}
