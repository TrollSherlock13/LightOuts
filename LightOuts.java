package a9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
/**
 * 
 * @author Long Nguyen, Dewang Goyal
 *
 */
@SuppressWarnings("serial")
public class LightOuts extends JFrame implements ActionListener {
	private LightButton[][] button;
	private JButton restart;
	private JButton quit;
	private JButton hint;
	private JLabel counter;
	private JPanel gamePanel;
	private JPanel countPanel;
	private int count;
	public static void main(String[] args) {
		JFrame mainWindow = new JFrame("LightsOut Game");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LightOuts().setVisible(true);
	}
	public LightOuts() {
		button = new LightButton[5][5];
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(5, 5));
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[i][j] = new LightButton(i, j);
				gamePanel.add(button[i][j]);
				button[i][j].addActionListener(this);
				button[i][j].setBackground(Color.white);
			}
		}
		randomize();
		countPanel = new JPanel();
		countPanel.add(counter = new JLabel("Counter: " + count));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(quit = new JButton("Quit"));
		buttonsPanel.add(hint = new JButton("Hint"));
		buttonsPanel.add(restart = new JButton("Restart"));
		quit.addActionListener(this);
		hint.addActionListener(this);
		restart.addActionListener(this);
		JPanel panel = new JPanel();
		setContentPane(panel);
		panel.setPreferredSize(new Dimension(500, 500));
		panel.setLayout(new BorderLayout());
		panel.add(gamePanel, "Center");
		panel.add(buttonsPanel, "South");
		panel.add(countPanel, "East");
		pack();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof LightButton) {
			LightButton abutton = (LightButton) e.getSource();
			int x = abutton.getRow();
			int y = abutton.getColumn();
			count++;
			counter.setText("Counter: " + count);
			blackAndWhite(button[x][y]);
			if (x > 0)
				blackAndWhite(button[x - 1][y]);
			if (x < button.length - 1)
				blackAndWhite(button[x + 1][y]);
			if (y > 0)
				blackAndWhite(button[x][y - 1]);
			if (y < button[x].length - 1)
				blackAndWhite(button[x][y + 1]);
		}
		if (e.getSource() == quit) {
			System.exit(0);
		}
		if (e.getSource() == hint) {
			boolean hint = true;
			outerloop: for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 5; j++) {
					if (button[i][j].getBackground() != Color.black) {
						hint  = false;
						break outerloop;
					}
				}
			}
			if (hint) {
				JOptionPane.showMessageDialog(null,
						"Bottom Row Lights	Top Row Clicks\n" + 
						"--- ..*** --------|---- ...+. ---\n" + 
						"--- .*.*. --------|---- .+..+ ---\n" + 
						"--- .**.* --------|---- +.... ---\n" + 
						"--- *...* --------|---- ...++ ---\n" + 
						"--- *.**. --------|---- ....+ ---\n" + 
						"--- **.** --------|---- ..+.. ---\n" + 
						"--- ***.. --------|---- .+... ---\n");
			}
			else
				JOptionPane.showMessageDialog(null, "Cannot give hint yet. Complete the first four rows to get hint.");
		}
		if (e.getSource() == restart) {
			count = 0;
			counter.setText("Counter: " + count);
			randomize();
		}
		if (gameWon()) {
			JOptionPane.showMessageDialog(null, "You won the game!");
		}
	}
	public void randomize() {
		for (int i = 0; i < button.length; i++) {
			for (int j = 0; j < button[i].length; j++) {
				button[i][j].setBackground(Color.black);
			}
		}
		Random rand = new Random();
		for (int a = 0; a < 10; a++) {
			int x = rand.nextInt(5);
			int y = rand.nextInt(5);
			blackAndWhite(button[x][y]);
			if (x > 0)
				blackAndWhite(button[x - 1][y]);
			if (x < button.length - 1)
				blackAndWhite(button[x + 1][y]);
			if (y > 0)
				blackAndWhite(button[x][y - 1]);
			if (y < button[x].length - 1)
				blackAndWhite(button[x][y + 1]);
		}
	}
	public void blackAndWhite(LightButton button) {
		if (button.getBackground().equals(Color.black))
			button.setBackground(Color.white);
		else
			button.setBackground(Color.black);
	}
	public boolean gameWon() {
		for (LightButton[] row : button) {
			for (LightButton abutton : row) {
				if (abutton.getBackground() == Color.white) {
					return false;
				}
			}
		}
		return true;
	}
}