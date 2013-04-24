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
 *  Lauren Kahn
 *  Michael Perrone
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;

/**
 * Model for the Iteration Table.
 *
 * @author Lauren Kahn
 * @author Michael Perrone
 *
 * @version Apr 8, 2013
 *
 */

@SuppressWarnings("serial")
public class IterationTableModel extends AbstractTableModel {

	protected String[] columnNames = { "ID", "Name", "Start Date", "End Date"};
	protected ArrayList<Object[]> data = new ArrayList<Object[]>();

	public static final String ASCENDING_SUFFIX = "\u2193";//the character displayed at the end of a header
	//if the column has been sorted in ascending order (down arrow)

	public static final String DESCENDING_SUFFIX = "\u2191";//the character displayed at the end of a header
	//if the column has been sorted in descending order(up arrow)

	/* Gets column count
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* Gets row count
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Sets column widths.
	 * 
	 * @param table table to set the column widths
	 */
	public void setColumnWidths(JTable table){
		table.getTableHeader().setReorderingAllowed(false);
		for (int i = 0 ; i < columnNames.length ; i ++){
			TableColumn column = table.getColumnModel().getColumn(i);

			if (i == 0) {
				column.setPreferredWidth(30); // ID
			} else if (i == 1) {
				column.setPreferredWidth(100); //NAME COLUMN
			} else if (i == 2) {
				column.setPreferredWidth(100); //STARTDATE
			} else if (i == 3) {
				column.setPreferredWidth(100); //ENDDATE
			}
		}
	}

	/* Gets column name
	 * @param col column to get the name of 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Adds row content.
	 * 
	 * @param rowContent content to add to the row
	 */
	public void addRow(Object[] rowContent) {
		data.add(rowContent);
	}

	/* Gets the value at a row and column
	 * @param row row to get value at
	 * @param col column to get value at
	 * @return Object value of the object that the method is getting
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {

		if (col < getColumnCount() && row < getRowCount() && col > -1
				&& row > -1) {
			return data.get(row)[col];
		} else
			return "null";
	}

	/**
	 * Adds a row to a requirement.
	 * 
	 * @param req Requirement to add a row to
	 */
	public void addRow(Iteration iteration){
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
		String start = "";
		String end = "";
		try {
			start = sdf.format(iteration.getStartDate());
			end = sdf.format(iteration.getEndDate());
		} catch (Exception e) {
		}

		Object[] r = {
				iteration.getId() ,
				iteration.getIterationName(),
				start,
				end};
		addRow(r);
	}

	/**
	 * Clears data.
	 * 
	 */
	public void clear(){
		data.clear();
	}

	/**
	 * Gets row ID.
	 * 
	 * @param row row to get ID of
	 * @return id of row
	 */
	public int getRowID(int row)
	{
		return Integer.parseInt( getValueAt(row, 0).toString() );
	}

	/** Sorts the table in ascending order bases on the column given. If it was already 
	 * sorted in ascending order, it will be sorted in descending order (and vice versa).
	 * It will also append a suffix to the header of the column being sorted indicating
	 * the order of the sort.
	 * 
	 *  @param col the column by which the table is sorted.
	 */
	public void sortTable(final int col, TableColumnModel cm){
		if(!(this.getValueAt(1,col) instanceof Comparable)){//can't sort if this column has no notion of an order
			return;
		}

		String currentHeader = cm.getColumn(col).getHeaderValue().toString();
		final boolean wasJustAscending = currentHeader.equals(columnNames[col]+ASCENDING_SUFFIX);//true if the header is the original
		//false if it has something appended to it

		Comparator<Object[]> comparator = new Comparator<Object[]>(){
			/**
			 * Compares two objects
			 * 
			 * @param a first object to compare
			 * @param b second object to compare
			 * @return the comparison
			 */
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(Object[] a, Object[] b){
				return ((Comparable<Comparable>) a[col]).compareTo((Comparable<Comparable>)b[col])*(wasJustAscending ? -1 : 1);
			}
			/**
			 * @see java.lang.Object#equals(java.lang.Object)
			 */
			@Override
			public boolean equals(Object o){
				return false;
			}
		};
		Object[][] dataArray = new Object[data.size()][data.get(0).length];
		for(int i=0; i<data.size(); i++){
			dataArray[i] = data.get(i);
		}

		Arrays.sort(dataArray , comparator);
		data = new ArrayList<Object[]>(Arrays.asList(dataArray));

		//reset the headers
		for(int i=0; i<cm.getColumnCount(); i++){
			cm.getColumn(i).setHeaderValue(columnNames[i]);
		}

		//set new header for ascending or descending
		currentHeader = cm.getColumn(col).getHeaderValue().toString();
		cm.getColumn(col).setHeaderValue(wasJustAscending ? currentHeader+DESCENDING_SUFFIX : currentHeader+ASCENDING_SUFFIX );
	}
}