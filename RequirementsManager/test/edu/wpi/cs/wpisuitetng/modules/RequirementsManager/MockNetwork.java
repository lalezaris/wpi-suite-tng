/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 *  Tianyu Li
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Mock network implemention for test. Copy from defectTracker
 * 
 * @author Tianyu Li
 *
 */
public class MockNetwork extends Network {
	protected MockRequest lastRequestMade = null;

	@Override
	public Request makeRequest(String path, HttpMethod requestMethod) {
		if (requestMethod == null) {
			throw new NullPointerException("requestMethod may not be null");
		}

		lastRequestMade = new MockRequest(defaultNetworkConfiguration, path,
				requestMethod);

		return lastRequestMade;
	}

	public MockRequest getLastRequestMade() {
		return lastRequestMade;
	}
}
