package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Mock network implemention for test. Copy from defectTracker
 * 
 * @author tli
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
