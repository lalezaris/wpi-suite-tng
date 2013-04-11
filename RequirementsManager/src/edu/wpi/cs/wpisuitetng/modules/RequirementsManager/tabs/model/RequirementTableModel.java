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
 *  Chris Hanna
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * Model for the Requirement Table
 * @author Chris Hanna
 * @modified by Tianyu Li on Apr 9
 * 
 * @version Apr 9th, 2013
 */

public class RequirementTableModel extends AbstractTableModel {

	protected String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate","Iteration", "Assigned", "Parent"};
    protected ArrayList<Object[]> data = new ArrayList<Object[]>();
    protected List<Requirement> requirements = new ArrayList<Requirement>();
    private boolean DEBUG = false;
    
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
//			if (col == 5 && (Integer) data.get(row)[col] == -1)
//				return "";

			return data.get(row)[col];
		} else
			return "null";
	}
    
    /**
     * Adds a row to a requirement
     * 
     * @param req Requirement to add a row to
     */
    public void addRow(Requirement req){
    	Object[] r = {
    			req.getId() ,
    			req.getTitle() ,
    			req.getDescription() ,
    			req.getStatus(),
    			req.getPriority(),
    			req.getEstimateEffort() ,
    			req.getIteration(),
    			req.getAssignee(),
    			req.getParentRequirementId()};
    	addRow(r);
    	requirements.add(req);
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
    
    
    /**
     * Make the estimate editable
     * 
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int col) {
    	if (data.get(row)[3].equals(RequirementStatus.INPROGRESS) ||
    			data.get(row)[3].equals(RequirementStatus.COMPLETE)) {
    		return false;
    	}
    	if (col == 5) {
    		return true;
    	}
    	else return false;
    }
    
    public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }
		
		try {
        	requirements.get(row).setEstimateEffort(Integer.parseInt((String)value));
		} catch (NumberFormatException e) {
			JLabel emptyLabel = new JLabel("");
			emptyLabel.setPreferredSize(new Dimension(175, 100));
			
			JFrame debugger = new JFrame("Input value must be integer");
			debugger.setVisible(true);
//			debugger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			debugger.getContentPane().add(emptyLabel, BorderLayout.CENTER);
			debugger.pack();
			debugger.setVisible(true);
		}

		Object[] element = data.get(row);
		element[5] = value;
        data.set(row, element);
        fireTableCellUpdated(row, col);  

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + getValueAt(i,j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
    
    public void clearRequirements() {
    	requirements.clear();
    }

	/**
	 * Gets the requirements
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}
}
