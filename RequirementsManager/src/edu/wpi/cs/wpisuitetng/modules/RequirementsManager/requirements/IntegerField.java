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
 * Tyler Stone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 * 
 * @author Tyler Stone, aquired at http://stackoverflow.com/questions/14319064/how-to-validate-a-jtextfield-to-accept-only-interger-numbers
 *
 * @version Mar 22, 2013
 */
@SuppressWarnings("serial")
public class IntegerField extends JTextField {

	/**
	 * Instantiates a new integer field.
	 */
	public IntegerField() {
		super();
	}

	/**
	 * Instantiates a new integer field.
	 *
	 * @param cols the columns
	 */
	public IntegerField( int cols ) {
		super( cols );
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTextField#createDefaultModel()
	 */
	@Override
	protected Document createDefaultModel() {
		return new UpperCaseDocument();
	}

	/**
	 * The Class UpperCaseDocument.
	 */
	static class UpperCaseDocument extends PlainDocument {
		/* (non-Javadoc)
		 * @see javax.swing.text.PlainDocument#insertString(int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		@Override
		public void insertString( int offs, String str, AttributeSet a )
				throws BadLocationException {
			if ( str == null ) {
				return;
			}

			char[] chars = str.toCharArray();
			boolean ok = true;

			for ( int i = 0; i < chars.length; i++ ) {

				try {
					Integer.parseInt( String.valueOf( chars[i] ) );
				} catch ( NumberFormatException exc ) {
					ok = false;
					break;
				}
			}

			if ( ok )
				super.insertString( offs, new String( chars ), a );
		}
	}
}
