package projekt;

import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7009745249238762641L;
	private JLabel theLabel = new JLabel();
	
	public Message(JLabel label){
		this.theLabel = label;
	}
	
	public JLabel getTheLabel(){
		return theLabel;
	}

}
