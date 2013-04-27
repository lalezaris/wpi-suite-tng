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

import javax.swing.AbstractAction;

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
		try {
			openWebpage(new URI("https://github.com/cmdunkers/wpi-suite-tng/wiki/User-Guide-to-Requirements-Manager"));
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