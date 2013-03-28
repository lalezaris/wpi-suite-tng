package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

public interface IRetrieveRequirementController {

	void runWhenRecieved(Requirement r);

	int getID();
}
