package net.cr.dao;

import java.util.List;

import net.cr.beans.PersonBean;

/**
 * 			PersonDAOIn.java : This interface contains the method to do operations on the person.
 * @author nagr0616
 *
 */
public interface PersonDAOIn {
	public PersonBean register(PersonBean person);
	public PersonBean login(PersonBean person);
	public PersonBean getRecords(PersonBean person);
	public PersonBean update(PersonBean person);
	public PersonBean getPersonRole(PersonBean personRole);
	public PersonBean removeUser(PersonBean bean);
	public List<PersonBean> getAllRecords(String username);
}
