package edu.wpi.cs.wpisuietng.modules.RequirementsManager.entitymanager;

import java.util.Date;
import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model.*;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * 
 * @author Michael French
 * @collaborators Sam Abradi, Evan Polekoff, Tianyu Li
 *
 */


public class RequirementStore implements EntityManager<Requirement>{
	Data db;
	
	public RequirementStore(Data data){
		db = data;
	}
	/*
	 * takes a string that is the JSON-ified representation of Requirement, and a session (project)
	 * returns the requirement in object form
	 * also puts the object in the DB indexable by ID
	 * */
	@Override
	public Requirement makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Requirement newRequirement = Requirement.fromJSON(content);	//still need to get fromJSON working, then this will work
		
		// TODO: increment properly, ensure uniqueness using ID generator.  This is a gross hack.
		newRequirement.setId(Count() + 1);
		/*
		List<ValidationIssue> issues = validator.validate(s, newRequirement, Mode.CREATE);
		if(issues.size() > 0) {
			// TODO: pass errors to client through exception
			for (ValidationIssue issue : issues) {
				System.out.println("Validation issue: " + issue.getMessage());
			}
			throw new BadRequestException();
		}
*/
		if(!db.save(newRequirement, s.getProject())) {
			throw new WPISuiteException();
		}
		return newRequirement;
	}

	/*
	 * accesses a requirement by ID from requirement
	 * returns an array of all requirements that fit this ID
	 * */
	@Override
	public Requirement[] getEntity(Session s, String id) throws NotFoundException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Requirement[] requirements = null;
		try {
			requirements = db.retrieve(Requirement.class, "id", intId, s.getProject()).toArray(new Requirement[0]);
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(requirements.length < 1 || requirements[0] == null) {
			throw new NotFoundException();
		}
		return requirements;
	}

	/*
	 * (non-Javadoc)
	 * GET ALL THE THINGS!!!
	 * literally returns an array of all requirements in the DB
	 */
	@Override
	public Requirement[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new Requirement("FU","UU"), s.getProject()).toArray(new Requirement[0]);
	}

	/*
	 * (non-Javadoc)
	 * to be written
	 */
	@Override
	public Requirement update(Session s, String content)
			throws WPISuiteException {
		//LOLWUT
		return null;
	}

	/*
	 *(non-Javadoc)
	 * saves the given requirement into the database
	 */
	@Override
	public void save(Session s, Requirement model) throws WPISuiteException {
		db.save(model, s.getProject());
	}

	/*
	 * this justt tests to make sure you are accesing things that you should be
	 */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * removes a requirement from the DB based on ID
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * deletes all the things in DB
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		ensureRole(s, Role.ADMIN);
		db.deleteAll(new Requirement("",""), s.getProject());
		
	}

	@Override
	public int Count() {
		// TODO: there must be a faster way to do this with db4o
		// note that this is not project-specific - ids are unique across projects
		return db.retrieveAll(new Requirement("foo", "foo")).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}


}
