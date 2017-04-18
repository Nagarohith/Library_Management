package net.cr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.cr.beans.PersonBean;
import net.cr.dao.PersonDAO;

public class TestPersonDAO {
	@Test
	public void testAddPerson(){
		PersonBean bean = new PersonBean();
		PersonDAO person = new PersonDAO();
		bean.setFname("Naga");
		bean.setLname("Rohith");
		bean.setUsername("nagr061");
		bean.setPassword("Naga@123");
		bean.setPhone("9591729534"); 
		bean.setEmail("rohith.nag5@gmail.com");
		bean.setRole("1");
		bean = person.register(bean);

		List<PersonBean> list = new ArrayList<PersonBean>();
		list = person.getAllRecords(bean.getUsername());

		assertEquals(bean.getUsername(),list.get(0).getUsername());
	}
	@Test
	public void testDeletePerson(){
		PersonBean bean = new PersonBean();
		PersonDAO person = new PersonDAO();
		List<PersonBean> list = new ArrayList<PersonBean>();

		bean.setUsername("nagr061");
		person.removeUser(bean);

		list = person.getAllRecords(bean.getUsername()); 

		assertTrue(list.size() == 0);

	}
}