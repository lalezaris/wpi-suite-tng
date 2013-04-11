package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.UpdateAllEstimateObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class UpdateAllRequirementsController {
	private final RetrieveAllRequirementsController controller;

	RequirementListPanel panel;
	public UpdateAllRequirementsController(RequirementListPanel panel){
		controller = new RetrieveAllRequirementsController(RefresherMode.TABLE);
		this.panel = panel;
	}
	
	public void update(){
		RequirementTableModel table = (RequirementTableModel)panel.getTable().getModel();
		System.out.println(table.getRequirements().size());
		System.out.println("Reach here");
		for (int i = 0 ; i < table.getRequirements().size(); i++) {
			if (table.getRequirements().get(i) != null) {
				saveRequirement(table.getRequirements().get(i));
				System.out.println(table.getRequirements().get(i));
				System.out.println("estimate: " + table.getRequirements().get(i).getEstimateEffort());
				System.out.println("Done req");
			}
		}
		((RequirementTableModel) panel.getTable().getModel()).clear();
		((RequirementTableModel) panel.getTable().getModel()).clearRequirements();
		System.out.println("Reach here for clear");
		//controller.refreshData();
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
