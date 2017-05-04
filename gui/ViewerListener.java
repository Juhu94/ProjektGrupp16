package gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public interface ViewerListener {
	public void updateViewer(ExtendedJLabel theLabel);
	public void updateViewer(boolean enableButtons);
	public void paintCharacter(int row, int col);
}
