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
 *  Tianyu Li
 *  Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import java.util.HashMap;

import java.util.Date;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;


/**
 * Model for the Requirement Table.
 *
 * @author Chris Hanna
 * 
 * @version Apr 9th, 2013
 */
@SuppressWarnings("serial")

public class RequirementTableModel extends AbstractTableModel {
	protected String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate","Iteration", "Assigned", "Parent ID"};
	protected List<Object[]> data = new ArrayList<Object[]>();
	protected List<Requirement> requirements = new ArrayList<Requirement>();
	protected List<Object[]> unSavedRequirements = new ArrayList<Object[]>();
	protected boolean isChange; 
	protected RequirementListPanel panel;

	private static final boolean DEBUG = false;


	public static final String ASCENDING_SUFFIX = "\u2193";//the character displayed at the end of a header
	//if the column has been sorted in ascending order (down arrow)

	public static final String DESCENDING_SUFFIX = "\u2191";//the character displayed at the end of a header
	//if the column has been sorted in descending order(up arrow)

	/**
	 * Instantiates a new requirement table model.
	 *
	 * @param panel the panel
	 */
	public RequirementTableModel(RequirementListPanel panel) {
		this.panel = panel;
		isChange = false;
	}

	/**
	 * Gets column count.
	 *
	 * @return the column count
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Gets row count.
	 *
	 * @return the row count
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}




	/**
	 * The Class ChangedCellRenderer.
	 */
	public class ChangedCellRenderer extends DefaultTableCellRenderer {

		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

			//Cells are by default rendered as a JLabel.
			JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

			//Get the status for the current row.
			RequirementTableModel tableModel = (RequirementTableModel) table.getModel();
			CellLocation cell = new CellLocation(row, col);
			if (tableModel.changedCells.contains(cell)){

				for (int i = 0 ; i < tableModel.changedCells.size(); i ++)
					if (tableModel.changedCells.get(i).equals(cell))
					{
						cell = tableModel.changedCells.get(i);
						break;
					}
				l.setToolTipText(cell.getMessage());

				if (cell.isValid())
					l.setBackground(MainView.getChangedColor());
				else l.setBackground(MainView.getChangedInvalidColor());
			} else l.setBackground(Color.white);

			//Return the JLabel which renders the cell.
			return l;

		}
	}

	/**
	 * Sets column widths.
	 * 
	 * @param table table to set the column widths
	 */
	public void setColumnWidths(JTable table){
		table.getTableHeader().setReorderingAllowed(false);
		for (int i = 0 ; i < table.getColumnModel().getColumnCount() ; i ++){
			TableColumn column = table.getColumnModel().getColumn(i);

			table.getColumnModel().getColumn(i).setCellRenderer(new ChangedCellRenderer());

			if (i == 0) {
				column.setPreferredWidth(30); //ID
			} else if (i == 1) {
				column.setPreferredWidth(100); //NAME
			} else if (i == 2) {
				column.setPreferredWidth(550); //DESCRIPTION
			} else if (i == 3) {
				column.setPreferredWidth(90); //STATUS
			} else if (i == 4) {
				column.setPreferredWidth(90); //PRIORITY
			} else if (i == 5) {
				column.setPreferredWidth(30); //ESTIMATE
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

	/**
	 * Gets column name.
	 *
	 * @param col column to get the name of
	 * @return the column name
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Adds row.
	 * 
	 * @param rowContent content to add to the row
	 */
	public void addRow(Object[] rowContent) {
		data.add(rowContent);
	}

	/**
	 * Gets the value at a row and column.
	 *
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
	 * Gets the unchanged value.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the unchanged value
	 */
	public Object getUnchangedValue(int row, int col){
		if (col < getColumnCount() && row < getRowCount() && col > -1
				&& row > -1) {
			return unSavedRequirements.get(row)[col];
		} else
			return "null";
	}

	/**
	 * Adds a row to a requirement.
	 * 
	 * @param req Requirement to add a row to
	 */
	public void addRow(Requirement req){
		String ass = req.getAssignee().toString();
		ass = ass.substring(1,ass.length()-1);

		Object[] r = {
				req.getId() ,
				req.getTitle() ,
				req.getDescription() ,
				req.getStatus(),
				req.getPriority(),
				req.getEstimateEffort() ,
				Iteration.getIterationById(req.getIterationId()),
				ass,
				req.getParentRequirementId() == -1 ? "" : req.getParentRequirementId()
		};

		addRow(r);
		requirements.add(req);
		unSavedRequirements.add(r.clone());
	}

	/**
	 * Update row.
	 *
	 * @param row the new row
	 * @param req the requirement to update
	 */
	public void updateRow(int row, Requirement req){
		String ass = req.getAssignee().toString();
		ass = ass.substring(1,ass.length()-1);
		Object[] r = {
				req.getId() ,
				req.getTitle() ,
				req.getDescription() ,
				req.getStatus(),
				req.getPriority(),
				req.getEstimateEffort() ,
				Iteration.getIterationById(req.getIterationId()),
				ass,
				req.getParentRequirementId() == -1 ? "" : req.getParentRequirementId()
		};
		data.set(row, r);
		requirements.set(row, req);
	}

	/**
	 * Removes row.
	 * 
	 * @param row row to remove
	 */
	public void removeRow(int row){
		data.remove(row);
		unSavedRequirements.remove(row);
	}

	/**
	 * Clears data.
	 * 
	 */
	public void clear(){
		data.clear();
		unSavedRequirements.clear();
	}


	/**
	 * Cancel changes.
	 */
	public void cancelChanges(){
		for (int i = 0 ; i < this.getRowCount(); i ++)
		{
			Object[] r = (unSavedRequirements.get(i)).clone();
			data.set(i, r);
			requirements.get(i).setTitle( (String) r[1]); //name
			requirements.get(i).setDescription( (String) r[2]); //description
			requirements.get(i).setStatus( (RequirementStatus) r[3]); //status
			requirements.get(i).setPriority( (RequirementPriority) r[4]); //priority
			requirements.get(i).setEstimateEffort( (Integer) r[5]); //estimate
			requirements.get(i).setIteration( (Iteration) r[6]); //iteration
		}	
		this.clearChangeVisualsDisregard();
		isChange = false;
		panel.setButtonsForNoChanges();
		panel.getFilterController().setEditable(!this.getIsChange());
	}

	/**
	 * Gets row ID.
	 * 
	 * @param row row to get ID of
	 * @return id of row
	 */
	public int getRowID(int row){
		return Integer.parseInt( getValueAt(row, 0).toString() );
	}

	/**
	 * Make the estimate editable.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if is cell editable
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		RMPermissionsLevel pLevel = CurrentUserPermissions
				.getCurrentUserPermission();

		if (pLevel != RMPermissionsLevel.ADMIN) {
			return false;
		}
		if (col == 0){ //ID should not be editable
			return false;
		}
		if (col > 6) { //Assigned, and Parent should not be editable
			return false;
		}
		if (requirements.get(row).getIteration() != Iteration.getBacklog()) {
			if (requirements.get(row).getIteration().getEndDate().before(new Date())) {
				if (col == 3) {
					return true;
				} else
					return false;
			}
		}
		if (requirements.get(row).getStatus().equals(RequirementStatus.INPROGRESS)) {
			if (col == 5) {
				return false;
			}
		}
		if (requirements.get(row).getStatus().equals(RequirementStatus.COMPLETE)) {
			if (col == 5 || col == 6) {
				return false;
			}
		}
		if (requirements.get(row).getStatus().equals(RequirementStatus.DELETED)) {
			if (col != 3) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Sets the value at.
	 *
	 * @param value the value
	 * @param row the row
	 * @param col the col
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {

		Requirement temp = requirements.get(row);
		String title = this.getColumnName(col);
		String changeMessage = CellLocation.getValidTooltip();
		if (!checkInput(value, temp, title)) {
			return;
		}

		if (title.equals("Parent ID")) {

			if(Integer.parseInt((String)value) != requirements.get(row).getId()){
				this.setChangedCell(true,row, col, true, changeMessage);
			}
			requirements.get(row).setId(Integer.parseInt((String)value));
		}
		if (title.equals("Name")) {
			if(!((String)value).equals(requirements.get(row).getTitle())){
				this.setChangedCell(true,row, col, true, changeMessage);
			}
			requirements.get(row).setTitle((String)value);
		}
		if (title.equals("Description")) {
			if(!((String)value).equals(requirements.get(row).getDescription())){
				this.setChangedCell(true,row, col, true, changeMessage);
			}
			requirements.get(row).setDescription((String)value);
		}
		if (title.equals("Status")) {


			boolean isValid = true;
			boolean isChange = false;

			if(((RequirementStatus)value).compareTo(requirements.get(row).getStatus()) != 0){
				isChange = true;

			}


			this.setChangedCell(isChange,row, col, isValid, changeMessage);
			requirements.get(row).setStatus((RequirementStatus)value);
			if (((RequirementStatus)value).equals(RequirementStatus.OPEN) &&
					requirements.get(row).getIteration() != Iteration.getBacklog()) {
				setValueAt(Iteration.getBacklog(), row, 6);
			}
		}
		if (title.equals("Priority")) {
			if(((RequirementPriority)value).compareTo(requirements.get(row).getPriority()) != 0){
				this.setChangedCell(true,row, col, true, changeMessage);
			}
			requirements.get(row).setPriority((RequirementPriority)value);
		}
		if (title.equals("Estimate")) {
			if(Integer.parseInt((String)value) != requirements.get(row).getEstimateEffort()){
				this.setChangedCell(true,row, col, true, changeMessage);
			}
			requirements.get(row).setEstimateEffort(Integer.parseInt((String)value));
		}	
		if (title.equals("Iteration")) {

			boolean isValid = true;
			boolean isChange = false;

			if(((Iteration)value).compareTo(requirements.get(row).getIteration()) != 0){


				isChange = true;
			}


			this.setChangedCell(isChange,row, col, isValid, changeMessage);
			requirements.get(row).setIteration((Iteration)value);
			requirements.get(row).setIterationId(((Iteration)value).getId());

			if (((Iteration)value).equals(Iteration.getBacklog()) &&
					requirements.get(row).getStatus() != RequirementStatus.OPEN
					) {
				setValueAt(RequirementStatus.OPEN, row, 3);
			}

			if (((Iteration)value) != Iteration.getBacklog() &&
					requirements.get(row).getStatus() != RequirementStatus.INPROGRESS) {
				setValueAt(RequirementStatus.INPROGRESS, row, 3);
			}
		}



		Object[] element = data.get(row);
		element[col] = value;
		data.set(row, element);
		fireTableCellUpdated(row, col); 
		panel.hideUpdateSuccessfully();
		this.logChangeErrors(row);
		
		panel.getFilterController().setEditable(!this.getIsChange());
		panel.setButtonsForChanges();

		if (DEBUG) {
			printDebugData();
		}
	}


	/**
	 * Log change errors.
	 *
	 * @param row the row to check
	 */
	public void logChangeErrors(int row){
		//move through the given row, and log problems...

		int titleCol = 1;
		int descCol = 2;
		int statusCol = 3; 
		int iterationCol = 6;

		//check for bugs, or check for good things!
		/* Rule One
		 * A status of open MUST mean that the iteration is backlog
		 */
		RequirementStatus status = (RequirementStatus)this.getChangedValue(row, statusCol);
		Iteration iter = (Iteration)this.getChangedValue(row, iterationCol);

		CellLocation statusChange = this.lookUpChange(row, statusCol);
		CellLocation iterChange = this.lookUpChange(row, iterationCol);

		if (status != RequirementStatus.DELETED){
			if (status == RequirementStatus.OPEN || status == RequirementStatus.NEW){
				if (iter.getId() != Iteration.getBacklog().getId()){ //ERROR!!!
					this.setChangedCell(true, row, statusCol, false, "An Open requirement must be in the Iteration Backlog.");
					this.setChangedCell(true, row, iterationCol, false, "An Open requirement must be in the Iteration Backlog.");
				} else{ //ALL IS WELL!!!
					CellLocation.setChangeValid(statusChange,row, statusCol, CellLocation.getValidTooltip(), this);
					CellLocation.setChangeValid(iterChange,row,iterationCol, CellLocation.getValidTooltip(), this);
				}
			} else{
				if (iter.getId() == Iteration.getBacklog().getId()){ //ERROR!!!

					this.setChangedCell(true, row, statusCol, false, "A requirement in Iteration Backlog must be Open.");
					this.setChangedCell(true, row, iterationCol, false, "A requirement in Iteration Backlog must be Open.");

				} else{ //ALL IS WELL!!!
					CellLocation.setChangeValid(statusChange,row, statusCol, CellLocation.getValidTooltip(), this);
					CellLocation.setChangeValid(iterChange,row,iterationCol, CellLocation.getValidTooltip(), this);
				}
			}
		} else{
			CellLocation.setChangeValid(statusChange,row, statusCol, CellLocation.getValidTooltip(), this);
			CellLocation.setChangeValid(iterChange,row,iterationCol, CellLocation.getValidTooltip(), this);
		}

		/* Rule Two
		 * Title can never be blank
		 */
		String title = (String)this.getChangedValue(row, titleCol);
		CellLocation titleChange = this.lookUpChange(row, titleCol);
		if (title == null || title.equals("")){ // PROBLEMO
			this.setChangedCell(true, row, titleCol, false, "Title cannot be blank");
		} else CellLocation.setChangeValid(titleChange, row, titleCol, CellLocation.getValidTooltip(), this);


		/* Rule Three
		 * Description can never be blank
		 */
		String description = (String)this.getChangedValue(row, descCol);
		CellLocation descriptionChange = this.lookUpChange(row, descCol);
		if (description == null || description.equals("")){ // PROBLEMO
			this.setChangedCell(true, row, descCol, false, "Description cannot be blank");
		} else CellLocation.setChangeValid(descriptionChange, row, descCol, CellLocation.getValidTooltip(), this);



	}


	/**
	 * Find the change by row and column.
	 *
	 * @param row the row
	 * @param col the col
	 * @return The change. If no change is found, then null is returned
	 */
	public CellLocation lookUpChange(int row, int col){
		for (int i = 0 ; i < changedCells.size(); i ++){
			CellLocation cell = changedCells.get(i);
			if (cell.getRow() == row && cell.getCol() == col)
				return cell;
		}
		return null;
	}

	/**
	 * Get the object of change at a table location. If the cell has NOT been changed, then
	 * this will return the value in the Requirement that is represented in that location.
	 *
	 * @param row the row
	 * @param col the column
	 * @return the changed value
	 */
	public Object getChangedValue(int row, int col){
		CellLocation change = this.lookUpChange(row, col);
		if (change!=null){
			return this.getValueAt(row, col);
		} else{
			if (row > -1 && row < unSavedRequirements.size()
					&& col > -1 && col < unSavedRequirements.get(row).length){
				return unSavedRequirements.get(row)[col];
			}
			return "null";
		}

	}

	/**
	 * The Class CellLocation.
	 */
	public static class CellLocation{

		/**
		 * Gets the valid default tooltip string.
		 *
		 * @return a default valid tooltip string.
		 */
		public static String getValidTooltip(){
			return "Click 'Update' to save all changes.";
		}

		/**
		 * Sets the the cell to be valid and updates its tooltip.
		 *
		 * @param cell the cell to set valid.
		 * @param row the row the cell is in.
		 * @param col the column the cell is in.
		 * @param msg the message to display in the tooltip.
		 * @param model the working table model
		 */
		public static void setChangeValid(CellLocation cell, int row, int col, String msg, RequirementTableModel model){
			if (cell!=null){
				cell.setValid(true);
				cell.setMessage(msg);
				model.panel.repaint();
			} else{
			}
		}

		private int row, col;
		private boolean isValid;
		private String message;



		/**
		 * Instantiates a new cell location.
		 *
		 * @param row the row
		 * @param col the col
		 */
		public CellLocation(int row, int col){
			this.row = row;
			this.col = col;
			isValid = false;
			message = "";
		}



		/**
		 * Gets the isValid.
		 *
		 * @return the isValid
		 */
		public boolean isValid() {
			return isValid;
		}


		/**
		 * Gets the message.
		 *
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}



		/**
		 * Sets the message.
		 *
		 * @param message the new message
		 */
		public void setMessage(String message) {
			this.message = message;
		}



		/**
		 * Sets the isValid.
		 *
		 * @param isValid the new valid
		 */
		public void setValid(boolean isValid) {
			this.isValid = isValid;
		}

		/**
		 * Gets the row.
		 *
		 * @return the row
		 */
		public int getRow() {
			return row;
		}

		/**
		 * Gets the col.
		 *
		 * @return the col
		 */
		public int getCol() {
			return col;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj){
			if (obj instanceof CellLocation){
				CellLocation other = (CellLocation)obj;
				return (other.row == row
						&& other.col == col);
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode(){
			return (1*row) + (3*col);
		}

		/**
		 * Sets the row.
		 *
		 * @param i the new row
		 */
		public void setRow(int i) {
			row = i;

		}


		/**
		 * Updates the position of the requirement in the table model.
		 *
		 * @param data the data
		 * @param oldData the old data
		 */
		public void updatePostion(List<Object[]> data, ArrayList<Object[]> oldData){
			//figure out where I was in the old requirement.
			int oldRow = row;
			int reqID = (Integer)oldData.get(oldRow)[0];
			int newRow = oldRow;
			//figure out what row the reqID is in now...
			for (int i = 0 ; i < data.size(); i ++){
				int testReqID = (Integer)data.get(i)[0];
				if (testReqID == reqID){
					newRow = i;
				}
			}

			row = newRow;

		}


	}

	private HashMap<Integer, CellLocation> cellDifferenceTable = new HashMap<Integer, CellLocation>();

	private ArrayList<CellLocation> changedCells = new ArrayList<CellLocation>();

	/**
	 * Log a change on the table.
	 *
	 * @param isChanged the is changed
	 * @param row the row
	 * @param col the col
	 * @param valid the valid
	 * @param message the message
	 */
	private void setChangedCell(boolean isChanged, int row, int col, boolean valid, String message){
		CellLocation cell = new CellLocation(row, col);
		if (isChanged){
			isChange = true;
			panel.setButtonsForChanges(); //There are no changes, so give the user the option to update their changes
			cell.setValid(valid);
			cell.setMessage(message);
			if (changedCells.contains(cell))
				changedCells.remove(cell);
			changedCells.add(cell);
		} else{
			if (changedCells.contains(cell)){
				changedCells.remove(cell);
			}
		}
		panel.repaint();

	}

	/**
	 * Display a reckless disregard for colors while clearing colors. Just Get rid of ALL the colors!
	 * 
	 */
	public void clearChangeVisualsDisregard(){
		changedCells.clear();
		panel.repaint();
	}

	/**
	 * Clear the change colors that are green!.
	 */
	public void clearChangeVisuals(){

		ArrayList<CellLocation> remove = new ArrayList<CellLocation>();
		ArrayList<CellLocation> all = new ArrayList<CellLocation>();
		for (int i = 0 ; i < changedCells.size(); i ++)
			all.add(changedCells.get(i));

		for (int i = 0 ; i < changedCells.size(); i ++){
			if (!changedCells.get(i).isValid()){
				if (!remove.contains(changedCells.get(i))){
					remove.add(changedCells.get(i));
					for (int j = 0 ; j < changedCells.size(); j ++){
						if (changedCells.get(j).getRow() == changedCells.get(i).getRow()){
							if (!remove.contains(changedCells.get(j)))
								remove.add(changedCells.get(j));
						}
					}
				}
			}
		}

		for (int i = 0 ; i < all.size(); i ++){
			if (!remove.contains(all.get(i)))
				changedCells.remove(all.get(i));
		}


	}

	/**
	 * Gets the changed locations.
	 *
	 * @return the changed locations
	 */
	public ArrayList<CellLocation> getChangedLocations(){
		return changedCells;
	}

	/**
	 * Method to check if the input value is legal.
	 *
	 * @param value the input value
	 * @param req the edited requirement
	 * @param title the edited part of requirement
	 * @return true if the input is legal, false otherwise
	 */
	private boolean checkInput(Object value, Requirement req, String title) {
		JFrame frame = new JFrame();
		int IDNumber;
		int estimate;

		if (title.equals("Parent ID")) {
			try {
				IDNumber = Integer.parseInt((String)value);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "The ID must be integer.");
				return false;
			}
			if (IDNumber < 0) {
				JOptionPane.showMessageDialog(frame, "The ID must be non-negative.");
				return false;
			}
		}

		if (title.equals("Estimate")) {
			try {
				estimate = Integer.parseInt((String)value);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "The estimate must be integer.");
				return false;
			}
			if (estimate < 0) {
				JOptionPane.showMessageDialog(frame, "The estimate must be non-negative.");
				return false;
			}
		}

		return true;
	}

	/**
	 * Prints the debug data.
	 */
	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();


	}

	/**
	 * Clear requirements.
	 */
	public void clearRequirements() {
		requirements.clear();
		unSavedRequirements.clear();
	}

	/**
	 * Gets the requirements.
	 *
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * Gets the unsaved requirements.
	 *
	 * @return the unsaved requirements
	 */
	public List<Object[]> getUnsavedRequirements(){
		return unSavedRequirements;
	}

	/**
	 * Gets the checks if is change.
	 *
	 * @return the checks if is change
	 */
	public boolean getIsChange() {
		return isChange;
	}

	/**
	 * Sets the checks if is change.
	 *
	 * @param value the new checks if is change
	 */
	public void setIsChange(boolean value) {
		isChange = value;
	}

	/**
	 * Sorts the table in ascending order bases on the column given. If it was already
	 * sorted in ascending order, it will be sorted in descending order (and vice versa).
	 * It will also append a suffix to the header of the column being sorted indicating
	 * the order of the sort.
	 *
	 * @param col the column by which the table is sorted.
	 * @param cm the TableColumnModel to use
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sortTable(final int col, TableColumnModel cm){
		if(!(this.getValueAt(1,col) instanceof Comparable)){//can't sort if this column has no notion of an order
			return;
		}

		//IF changes, disable sorts
		if (panel != null && panel.getTable().isEditing())
			return;

		String currentHeader = cm.getColumn(col).getHeaderValue().toString();
		final boolean wasJustAscending = currentHeader.equals(columnNames[col]+ASCENDING_SUFFIX);//true if the header is the original
		//false if it has something appended to it

		Comparator<Object[]> comparator = new Comparator<Object[]>(){

			@Override
			public int compare(Object[] a, Object[] b){
				return ((Comparable<Comparable>) a[col]).compareTo((Comparable<Comparable>)b[col])*(wasJustAscending ? -1 : 1);
			}
			@Override
			public boolean equals(Object o){
				return false;
			}
		};
		Object[][] dataArray = new Object[data.size()][data.get(0).length];
		for(int i=0; i<data.size(); i++){
			dataArray[i] = data.get(i);
		}

		for(int j=0; j < dataArray.length; j++){//8 is the Parent column
			if(dataArray[j][8].equals("")){
				dataArray[j][8] = -1;
			}
		}

		cellDifferenceTable.clear();

		for(int i = 0 ; i < changedCells.size() ; i ++){
			int r = changedCells.get(i).getRow();
			Integer reqId = (Integer)data.get(r)[0];
			cellDifferenceTable.put(reqId, changedCells.get(i));
		}

		//Sort data
		Arrays.sort(dataArray , comparator);
		data = new ArrayList<Object[]>(Arrays.asList(dataArray));

		for (int i = 0 ; i < data.size(); i ++){
			if (cellDifferenceTable.get(data.get(i)[0])!=null){
				cellDifferenceTable.get(data.get(i)[0]).setRow(i);
			}
		}

		if(panel != null)
			panel.repaint();

		for(int j=0; j < dataArray.length; j++){//8 is the Parent column
			if(dataArray[j][8].equals(-1)){
				dataArray[j][8] = "";
			}
		}

		//reset the headers
		for(int i=0; i<cm.getColumnCount(); i++){
			cm.getColumn(i).setHeaderValue(columnNames[i]);
		}

		//set new header for ascending or descending
		currentHeader = cm.getColumn(col).getHeaderValue().toString();
		cm.getColumn(col).setHeaderValue(wasJustAscending ? currentHeader+DESCENDING_SUFFIX : currentHeader+ASCENDING_SUFFIX );
	}
}