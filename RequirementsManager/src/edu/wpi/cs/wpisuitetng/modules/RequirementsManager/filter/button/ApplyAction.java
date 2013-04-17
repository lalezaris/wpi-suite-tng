package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.rules.RuleTargetException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;

public class ApplyAction extends AbstractAction{

	FilterController controller;
	public ApplyAction(FilterController filterController) {
		this.controller = filterController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.getModel().setModelFromPanel(controller.getPanel());
		
		
		RequirementTableModel m = ((RequirementTableModel)controller.getListPanel().getTable().getModel());
		List<Requirement> inList = m.getRequirements();
		Requirement[] in = new Requirement[inList.size()];
		for (int i = 0 ; i < inList.size(); i ++)
			in[i] = inList.get(i);
		Requirement[] filtered = null;
		try {
			filtered = controller.getModel().getFilter().getFilteredObjects(in);
		} catch (RuleTargetException e1) {
			//e1.printStackTrace();
			System.out.println("Filter Error: " + e1.getMessage());
			return;
		}
		System.out.println("FILTERED REQUIREMENTS");
		for (int i = 0 ; i < filtered.length; i ++)
			System.out.println(filtered[i].toJSON());
		//controller.getListPanel().addRequirements(filtered);
		
	}

}
