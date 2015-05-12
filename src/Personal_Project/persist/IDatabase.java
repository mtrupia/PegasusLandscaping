package Personal_Project.persist;

import java.util.List;

import Personal_Project.model.*;

public interface IDatabase {
	public List<Customer> getCustomers();
	public List<Service> getServices();
	public List<Payment> getPayments();
	
	public void addCustomer(final String fname, final String lname, final String address, final String city, final String state, final int zip, final int daily);
	public void addService(final int customerId, final int cost, final String comment);
	public void addPayment(final int customerId, final int paid, final String comment);
	
	public void updateCustomers(final long lastCut, final int customerId, final int active);
	public void updateCustomerDaily(final int daily, final int customerId);
	public void updateCustomerTotal(final int owed, final int paid, final int id);
	
	
	public void removeCustomer(final int customerId);
	public void removeService(final int id);
	public void removePayment(final int id);
	
	public void backUpData();
	public void restoreData();
}
