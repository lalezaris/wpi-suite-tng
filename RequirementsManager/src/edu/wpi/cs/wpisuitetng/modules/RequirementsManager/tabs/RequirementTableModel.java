/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;

/**
 * @author Chris Hanna
 *
 */
public class RequirementTableModel extends AbstractTableModel {

	private String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate","Iteration", "Assigned"};
    private ArrayList<Object[]> data = new ArrayList<Object[]>();

    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

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
		}
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    
    public void addRow(Object[] rowContent) {
    	data.add(rowContent);
    }
    
    @Override
    public Object getValueAt(int row, int col) {

    	if (col < getColumnCount() && row < getRowCount() && col > -1 && row > -1){
    	if (col == 5 && (Integer)data.get(row)[col] == -1)
    		return "";
    	
    		return data.get(row)[col];
    	}
    	else return "null";
    }
    
    public void addRow(Requirement req){
    	Object[] r = {
    			req.getId() ,
    			req.getTitle() ,
    			req.getDescription() ,
    			req.getStatus() ,
    			req.getPriority() ,
    			req.getEstimateEffort() ,
    			req.getIteration(),
    			req.getAssignee().getUsername()};
    	addRow(r);
    }
    
    public void removeRow(int row){
    	data.remove(row);
    }
    
    public void clear(){
    	data.clear();
    }
    
    public int getRowID(int row)
    {
    	return Integer.parseInt( getValueAt(row, 0).toString() );
    
    }
    
   
    
    
}
