package gui;

import javax.swing.Icon;
import javax.swing.JLabel;

public interface ViewerListener {
	public void updateViewer();
	public void updateInfoRutaSteps(String text);
	public void updateInfoRutaMap(String text);
	public void updateInfoRutaTreasure(String text);
	public void enableButtons(String text);
	public void addConnectedUser(String namn);
	public void removeConnectedUsers();
	public void moveIcon(String name, int row, int col, boolean visible);
	public void chooseCharFrame();
	public void updateChooseCharFrame(boolean svullo, boolean tjoPang, boolean theRat, boolean markisen, boolean hannibal, boolean hook);
	public void setAvailableTarget(String character, String username);
	public void getTarget();
	public void setIconSleep(String name, boolean sleeping);
	public void setWaterIcon(String name);
	public void showVictory(String winner);
}
