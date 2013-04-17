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
 *  Sam Lalezari
 *  
 *  Code Used From: http://stackoverflow.com/questions/8197167/word-wrap-in-jlist-items
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Cell Renderer for HistoryView list to enable wrapping.
 *
 * @author Sam Lalezari
 *
 * @version Apr 4, 2013
 *
 */
@SuppressWarnings("serial")
public class HistoryViewCellRenderer extends DefaultListCellRenderer {
	public static final String HTML_1 = "<html><body style='width: ";
	public static final String HTML_2 = "px'>";
	public static final String HTML_3 = "</html>";
	private int width;	
	
	/**
	 * Constructor for HistoryViewCellRenderer.
	 * 
	 * @param w the width
	 */
	public HistoryViewCellRenderer(int w){
		
		this.width = w;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Object> list,
			Object value, int index, boolean isSelected, boolean cellHasFocus) {
		String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
				+ HTML_3;
		return super.getListCellRendererComponent(list, text, index, isSelected,
				cellHasFocus);
	}
}