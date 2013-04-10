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
 *  Lauren
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;


/**
 * Model for the Iteration Table
 *
 * @author Lauren
 *
 * @version Apr 8, 2013
 *
 */

	@SuppressWarnings("serial")
	public class IterationTableModel extends AbstractTableModel {

		protected String[] columnNames = { "ID", "Name", "Start Date", "End Date"};
	    protected ArrayList<Object[]> data = new ArrayList<Object[]>();
	    
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
	     * Sets column widths
	     * 
	     * @param table table to set the column widths
	     */
	    public void setColumnWidths(JTable table){
	    	table.getTableHeader().setReorderingAllowed(false);
			for (int i = 0 ; i < 8 ; i ++){
				TableColumn column = table.getColumnModel().getColumn(i);
			
				if (i == 0) {
			    	column.setPreferredWidth(30); // ID
			    } else if (i == 1) {
			        column.setPreferredWidth(100); //NAME COLUMN
			    } else if (i == 2) {
			    	column.setPreferredWidth(550); //DESC COLUMN
			    } else if (i == 3) {
			    	column.setPreferredWidth(90); //DESC STATUS
			    } else if (i == 4) {
			    	column.setPreferredWidth(90); //DESC PRIORITY
			    } else if (i == 5) {
			    	column.setPreferredWidth(30); //DESC ESTIMATE
			    } else if (i == 6) {
			    	column.setPreferredWidth(70); //ITERATION
			    } else if (i == 7) {
			    	column.setPreferredWidth(100); //ASSIGNEE
			    }
			    else if (i == 8) {
			    	column.setPreferredWidth(30); //PARENT ID
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
	     * Adds row
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
				if (col == 5 && (Integer) data.get(row)[col] == -1)
					return "";

				return data.get(row)[col];
			} else
				return "null";
		}
	    
	    /**
	     * Adds a row to a requirement
	     * 
	     * @param req Requirement to add a row to
	     */
	    public void addRow(Iteration iteration){
	    	Object[] r = {
	    			iteration.getId() ,
	    			iteration.getIterationName(),
	    			iteration.getStartDate(),
	    			iteration.getEndDate()};
	    	addRow(r);
	    }
	    
	    /**
	     * Removes row
	     * 
	     * @param row row to remove
	     */
	    public void removeRow(int row){
	    	data.remove(row);
	    }
	    
	    /**
	     * Clears data
	     * 
	     */
	    public void clear(){
	    	data.clear();
	    }
	    
	    /**
	     * Gets row ID
	     * 
	     * @param row row to get ID of
	     * @return id of row
	     */
	    public int getRowID(int row)
	    {
	    	return Integer.parseInt( getValueAt(row, 0).toString() );
	    
	    }
	    
}
