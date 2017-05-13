package gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public interface ViewerListener {
	public void updateViewer(ExtendedJLabel theLabel);
	public void updateViewer();
	public void updateInfoRuta(String text);
	public void paintCharacter(int newRow, int newCol, int oldRow, int oldCol);
	public void enableButtons(String text);
	public void addConnectedUser(String namn);
}
