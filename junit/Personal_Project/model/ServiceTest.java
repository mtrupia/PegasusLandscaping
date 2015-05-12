package Personal_Project.model;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;



public class ServiceTest {
	Service me;
	
	@Before
	public void setUp() {
		Date before = new Date();
		me = new Service(0, 0, 0, "No Payment", before.getTime());
	}
	
	@Test
	public void testSetId() {
		assertEquals(0, me.getId());
		me.setId(1);
		assertEquals(1, me.getId());
	}
	
	@Test
	public void testSetCustomer_Id() {
		assertEquals(0, me.getCustomerId());
		me.setCustomerId(1);
		assertEquals(1, me.getCustomerId());
	}
	
	@Test
	public void testSetCost() {
		assertEquals(0, me.getCost());
		me.setCost(1);
		assertEquals(1, me.getCost());
	}
	
	@Test
	public void testSetComment() {
		assertEquals("No Payment", me.getComment());
		me.setComment(" ");
		assertEquals(" ", me.getComment());
	}
}
