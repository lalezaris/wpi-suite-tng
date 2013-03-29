package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;


public interface IRetrieveRequirementController {

	void runWhenRecieved(Requirement r);

	int getID();
}
