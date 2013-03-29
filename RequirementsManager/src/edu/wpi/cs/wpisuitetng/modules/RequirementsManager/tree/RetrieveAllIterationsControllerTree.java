/**
* This file was developed for CS3733: Software Engineering
* The course was taken at Worcester Polytechnic Institute.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* Sam Abradi
*/


package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieves all of the iterations from the database
 *
 * @author Sam Abradi
 *
 * @version Mar 21, 2013
 *
 */
public class RetrieveAllIterationsControllerTree {
    /** The search Iterations view */
    protected ReqTreeModel view;
    
    /** The Iterations data retrieved from the server */
    protected Iteration[] data = null;
    
    /**
     * Constructs a new RetrieveAllIterationsControllerTree
     *
     * @param view the search Iterations view
     */
    public RetrieveAllIterationsControllerTree(ReqTreeModel view) {
	this.view = view;
    }
    
    /**
     * Sends a request for all of the Iterations
     */
    public void refreshData() {	
	final RequestObserver requestObserver = new RetrieveAllIterationsRequestObserverTree(this);
	Request request;
	request = Network.getInstance().makeRequest("iterationsmanager/iteration", /*is this ok? ->*/ HttpMethod.GET);
	request.addObserver(requestObserver);
	request.send();
	System.out.println("SENT REFRESH REQUEST");
    }

    /**
     * This method is called by the {@link RetrieveAllIterationsRequestObserverTree} when the
     * response is received
     *
     * @param Iterations an array of Iterations returned by the server
     */
    public void receivedData(Iteration[] Iterations) {	
	if (Iterations.length > 0) {
	    // save the data
		
	    this.data = Iterations;
	    Refresher.getInstance().setLastKnownIterations(Iterations);
	    this.view.addIterations(Refresher.getInstance().getInstantIterations());
	}
	else {
	    // do nothing, there are no Iterations
	}
	
    }
    
    /**
     * This method is called by the {@link RetrieveAllIterationsRequestObserverTree} when an
     * error occurs retrieving the Iterations from the server.
     */
    public void errorReceivingData(String error) {
	//JOptionPane.showMessageDialog(view, "An error occurred retrieving Requirements from the server. " + error,
	//"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
    }
    
}
