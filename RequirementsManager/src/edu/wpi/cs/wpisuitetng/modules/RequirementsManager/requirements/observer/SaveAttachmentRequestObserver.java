package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveAttachmentController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class SaveAttachmentRequestObserver implements RequestObserver {
	SaveAttachmentController controller;
	
	public SaveAttachmentRequestObserver(SaveAttachmentController controller){
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {

		Request request = (Request) iReq;
		
		ResponseModel response = request.getResponse();
		if((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)){
			Attachment attachments = Attachment.fromJSON(response.getBody());
			System.out.println("file is stored with this id: " + attachments.getId());
			

			
			controller.recievedData(attachments);
		}

	}

	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method hub
		System.out.println("ERROREOOREOORERROREOOREERRERROREOORERROREOORERROREOOREOOERROREOOREOOERROREERROREOOREOOR");
		System.out.println(iReq.getBody());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method flub
		System.out.println("FAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAILFAIL");
	}

}
