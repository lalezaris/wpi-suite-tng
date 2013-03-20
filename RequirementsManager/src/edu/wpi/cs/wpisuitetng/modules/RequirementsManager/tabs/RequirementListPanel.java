/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * @author lty
 *
 */
public class RequirementListPanel extends JPanel {

	
	
	public RequirementListPanel(RequirementListView parent){
		add(new JLabel("REQ LIST VIEW"));
	}
	
	
	public void addRequirement(Requirement req){
		add(new JLabel("REQ:" + req.getTitle()));
	}
}
