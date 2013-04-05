package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History;

public class ChangeReqTitle implements IChange {
	
	String oldTitle;
	
	@Override
	public String printHR() {
		return oldTitle;
	}
	
}
