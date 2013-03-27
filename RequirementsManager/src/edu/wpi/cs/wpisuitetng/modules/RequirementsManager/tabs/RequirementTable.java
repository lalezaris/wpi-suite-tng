/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;

/**
 * @author Chris Hanna
 *
 */
public class RequirementTable extends AbstractTableModel {

	private String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate", "Assigned"};
    private ArrayList<Object[]> data = new ArrayList<Object[]>();

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }


    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    
    public void addRow(Object[] rowContent) {
    	data.add(rowContent);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        
    	return data.get(row)[col];
    }
    
    public void addRow(Requirement req){
    	Object[] r = {
    			req.getId() ,
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
    
    public int getRowID(int row)
    {
    	return Integer.parseInt((String)getValueAt(row, 0));
    
    }
 
}
