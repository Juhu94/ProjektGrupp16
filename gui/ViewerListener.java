package gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public interface ViewerListener {
	public void updateViewer(ExtendedJLabel theLabel);
	public void updateViewer();
	public void paintCharacter(int newRow, int newCol, int oldRow, int oldCol);
}
