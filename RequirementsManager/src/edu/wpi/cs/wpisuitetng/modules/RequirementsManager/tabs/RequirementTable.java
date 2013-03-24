/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * @author Chris Hanna
 *
 */
public class RequirementTable extends AbstractTableModel {

	private String[] columnNames = {"ID", "Name", "Description", "Status", "Priority", "Estimate", "Assigned"};
    private ArrayList<Object[]> data = new ArrayList<Object[]>();

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public void addRow(Object[] rowContent) {
    	data.add(rowContent);
    }
    
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }
    
    public void addRow(Requirement req){
    	Object[] r = {req.getId() ,
    			req.getTitle() ,
    			req.getDescription() ,
    			req.getStatus() ,
    			req.getPriority() ,
    			req.getEstimateEffort() ,
    			req.getAssignee().getUsername()};
    	addRow(r);
    }
    
    public void removeRow(int row){
    	data.remove(row);
    }
    
    public void clear(){
    	data.clear();
    }
 
}
