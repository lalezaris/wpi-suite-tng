package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Mock request implemention for test. Copy from defectTracker
 * 
 * @author tli
 *
 */
public class MockRequest extends Request {
	protected boolean sent = false;

	public MockRequest(NetworkConfiguration networkConfiguration, String path, HttpMethod requestMethod) {
		super(networkConfiguration, path, requestMethod);
	}

	@Override
	public void send() throws IllegalStateException {
		// don't actually send
		sent = true;
	}
	
	public boolean isSent() {
		return sent;
	}
}
