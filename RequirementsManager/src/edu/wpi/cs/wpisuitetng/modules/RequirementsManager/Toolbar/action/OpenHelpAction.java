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
 *  Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * Action to open the User Guide webpage.
 * 
 * @author Arica Liu
 *
 * @version Apr 16, 2013
 *
 */

@SuppressWarnings("serial")
public class OpenHelpAction extends AbstractAction {
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
			URL url = ConfigManager.getConfig().getCoreUrl();
			System.out.println("URL: " + url.toString());
			String urlString = url.toString();
			urlString = urlString.substring(0, urlString.length() - 3) + "UserManual.pdf";
			try {
				openWebpage(new URI(urlString));
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
	}

	/**
	 * Default constructor.
	 * 
	 */
	public OpenHelpAction() {
		super("User Guide");
	}

	/**
	 * Open a webpage.
	 *
	 * @param uri the uri
	 */
	public static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}