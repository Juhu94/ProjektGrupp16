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
	public void removeConnectedUsers();
	public void moveIcon(String name, int row, int col);
	public void chooseCharFrame();
	public void updateChooseCharFrame(boolean svullo, boolean tjoPang, boolean theRat, boolean markisen, boolean hannibal, boolean hook);
	public void setAvailableTarget(String character, String username);
	public void getTarget();
	public void setIconSleep(String name, boolean sleeping);
}
