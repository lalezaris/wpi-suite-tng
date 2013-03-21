/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * Insert Description Here
 *
 * @author lty
 *
 * @version Mar 21, 2013
 *
 */
public class RequirementListPanel extends JPanel {

	JTextArea list;
	
	public RequirementListPanel(RequirementListView parent){
		list = new JTextArea();
		list.setEditable(false);
		
		list.setText("REQ LIST VIEW\n");
		add(list);
	}
	
	
	public void addRequirement(Requirement req){
		list.append(req.getTitle() + "\n");
	}


	public void clearList() {
		list.setText("REQ LIST VIEW" + "\n");
	}
}
