package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller for update requirements in the table view.
 *
 * @author Chris Hanna
 * @author Tianyu Li
 *
 * @version Apr 11th, 2013
 *
 */
public class UpdateAllRequirementsController {

	RequirementListPanel panel;
	public UpdateAllRequirementsController(RequirementListPanel panel){
		this.panel = panel;
	}
	
	public void update(){
		RequirementTableModel table = (RequirementTableModel)panel.getTable().getModel();
		System.out.println(table.getRequirements().size());
		System.out.println("Reach here");
		for (int i = 0 ; i < table.getRequirements().size(); i++) {
			if (table.getRequirements().get(i) != null) {
				saveRequirement(table.getRequirements().get(i));
			}
		}
		((RequirementTableModel) panel.getTable().getModel()).clear();
		((RequirementTableModel) panel.getTable().getModel()).clearRequirements();
	}
	
	private void saveRequirement(Requirement r){
		System.out.println(r.getId());
		Request request = Network.getInstance().makeRequest(
				"requirementsmanager/requirement/"
						+ r.getId(), HttpMethod.POST);
		String JsonRequest = r.toJSON();
		request.setBody(JsonRequest);
		request.addObserver(new UpdateRequirementObserver(this));
		request.send();
	}
	
}
