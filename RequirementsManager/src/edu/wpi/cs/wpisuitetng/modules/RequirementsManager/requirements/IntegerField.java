/**************************************************
 * This file was taken from Stack Overflow under the cc-wiki license
 * the author can be seen belwo with the link, going to the authors profile in 
 * Stack Overflow. Also the content was found at:
 * http://stackoverflow.com/questions/14319064/how-to-validate-a-jtextfield-to-accept-only-interger-numbers
 *
 * Contributors:
 * David Buzatto, http://stackoverflow.com/users/469201/davidbuzatto
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * A JTextField that accepts only integers.
 * The Content of this class is from Stack Overflow.
 * It can be found at http://stackoverflow.com/questions/14319064/how-to-validate-a-jtextfield-to-accept-only-interger-numbers
 * 
 * @author David Buzatto, http://stackoverflow.com/users/469201/davidbuzatto 
 *
 * @version Mar 22, 2013
 */
@SuppressWarnings("serial")
public class IntegerField extends JTextField {

	/**
	 * Instantiates a new integer field.
	 */
	public IntegerField() {}

	/**
	 * Instantiates a new integer field.
	 *
	 * @param cols the columns
	 */
	public IntegerField( int cols ) {
		super( cols );
	}

	/**
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
		/**
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
