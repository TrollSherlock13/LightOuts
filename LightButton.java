package a9;
import javax.swing.JButton;
@SuppressWarnings("serial")
public class LightButton extends JButton {
	private int rows;
	private int columns;
	//private int counter;
	public LightButton(int i, int j) {
		rows = i;
		columns = j;
	}
	public int getRow() {
		return rows;
	}
	public int getColumn() {
		return columns;
	}
}