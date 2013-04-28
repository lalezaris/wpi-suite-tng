package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import static org.junit.Assert.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class testTree {
	Requirement r;
	ReqTreeNode rtn;
	ReqTreeModel rtm;
	TreeView tv;
	
	/**
	 * setup the variable for this test
	 */
	@Before
	public void setup(){
		r = new Requirement();
		r.setId(1);
		r.setDescription("desc");
		rtn = new ReqTreeNode(r);
		rtm = new ReqTreeModel(new DefaultMutableTreeNode());
		tv = new TreeView();
	}
	
	/**
	 * test the ReqTreeNode class
	 */
	@Test
	public void testReqTreeNote(){
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		assertEquals(r, rtn.getRequirement());
		assertEquals(r.getTitle(), rtn.getName());
	}
	
	/**
	 * test the ReqTreeModel class
	 */
	@Test
	public void testReqTreeModel(){
		Requirement[] reqs = {r};
		rtm.fillTree(null);
		assertEquals(0, rtm.getRequirements().length);
		rtm.fillTree(reqs);
		assertEquals(1, rtm.getRequirements().length);
		r.setStatus(RequirementStatus.DELETED);
		rtm.fillTree(null);
		
	}
	
	/**
	 * test the TreeView class
	 */
	@Test
	public void testTreeView(){
		Requirement[] reqs = {r};
		tv.clearStatus();
		assertEquals("", tv.status.getText());
		tv.setStatus("a status");
		assertEquals("a status", tv.status.getText());
		tv.refreshButton.doClick();
		assertEquals("", tv.status.getText());
		tv.treeModel.fillTree(reqs);
		assertEquals(r, tv.lookUpRequirement(r.getId()));
		assertEquals(null, tv.lookUpRequirement(2));
		
	}
}
