/*
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about User information as the User is constructed.
 */
public class UserObserver implements RequestObserver{
	
	/** The view. */
	BarChartView view;
	
	/**
	 * This method is called when information about an User
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param view the view
	 */
	public UserObserver(BarChartView view){
		this.view = view;

	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		User[] users = builder.create().fromJson(response.getBody(), User[].class);
		//this.panel.setAllusers(users);
		this.view.recieveServerUsers(users);
		//CurrentUserPermissions.updateCurrentUserPermissions();
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
