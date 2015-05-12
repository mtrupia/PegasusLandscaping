package Personal_Project.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;



public class PaymentTest {
	Payment me;
	
	@Before
	public void setUp() {
		me = new Payment(0, 0, 0, "No Payment", new Date().getTime());
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
	public void testSetPaid() {
		assertEquals(0, me.getPaid());
		me.setPaid(1);
		assertEquals(1, me.getPaid());
	}
	
	@Test
	public void testSetComment() {
		assertEquals("No Payment", me.getComment());
		me.setComment(" ");
		assertEquals(" ", me.getComment());
	}
}
