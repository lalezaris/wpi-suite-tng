package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;

public class EditAcceptanceTestController implements ActionListener{
	private final AcceptanceTestsView view;

	/**
	 * Default constructor
	 * 
	 * @param view
	 */
	public EditAcceptanceTestController(AcceptanceTestsView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AcceptanceTest a = new AcceptanceTest(view.getTitleTxt(), view.getBodyTxt());
		if(view.notReady()){
			//do nothing no text has been entered
		} else {
			view.replaceTest(a);
			System.out.println("Tests in List: " + view.getListSize());
			view.updateList();
			view.clearTitleTxt();
			view.clearBodyTxt();
		}		
	}
}
