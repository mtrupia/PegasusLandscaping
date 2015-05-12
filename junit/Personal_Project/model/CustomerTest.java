package Personal_Project.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;



public class CustomerTest {
	Customer me;
	
	@Before
	public void setUp() {
		me = new Customer(0, "Michael", "Trupia", "xxxxx", "York","PA", 17404, 0, 0, 0, 0, new Date().getTime(), 1);
	}
	
	@Test
	public void testSetId() {
		assertEquals(0, me.getId());
		me.setId(1);
		assertEquals(1, me.getId());
	}
	
	@Test
	public void testSetFName() {
		assertEquals("Michael", me.getFName());
		me.setFName("Mike");
		assertEquals("Mike", me.getFName());
	}
	
	@Test
	public void testSetLName() {
		assertEquals("Trupia", me.getLName());
		me.setLName("Mike");
		assertEquals("Mike", me.getLName());
	}
	
	@Test
	public void testSetAddress() {
		assertEquals("xxxxx", me.getAddress());
		me.setAddress("Mike");
		assertEquals("Mike", me.getAddress());
	}
	
	@Test
	public void testSetCity() {
		assertEquals("York", me.getCity());
		me.setCity("Mike");
		assertEquals("Mike", me.getCity());
	}
	
	@Test
	public void testSetState() {
		assertEquals("PA", me.getState());
		me.setState("Mike");
		assertEquals("Mike", me.getState());
	}
	
	@Test
	public void testSetZip() {
		assertEquals(17404, me.getZip());
		me.setZip(21236);
		assertEquals(21236, me.getZip());
	}
	
	@Test
	public void testSetOwed() {
		assertEquals(0, me.getOwed());
		me.setOwed(21236);
		assertEquals(21236, me.getOwed());
	}
	
	@Test
	public void testSetPaid() {
		assertEquals(0, me.getPaid());
		me.setPaid(21236);
		assertEquals(21236, me.getPaid());
	}
	
	@Test
	public void testSetBalance() {
		assertEquals(0, me.getBalance());
		me.setBalance(21236);
		assertEquals(21236, me.getBalance());
	}
}
