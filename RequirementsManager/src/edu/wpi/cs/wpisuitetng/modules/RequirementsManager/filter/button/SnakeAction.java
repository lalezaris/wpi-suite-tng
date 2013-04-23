
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;


public class SnakeAction extends AbstractAction{

	public SnakeAction() {
		super("Om Nom Nom");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainTabController.getController().addSnakeTab();
	}

}
