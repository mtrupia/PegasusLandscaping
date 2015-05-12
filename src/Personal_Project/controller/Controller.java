package Personal_Project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Personal_Project.model.*;
import Personal_Project.persist.IDatabase;
import Personal_Project.persist.DatabaseProvider;

public class Controller {
	// All the info we need :D
	private static IDatabase db = DatabaseProvider.getInstance();
	
	public static List<Customer> getCustomes() {
		List<Customer> meme = db.getCustomers();
		meme.remove(0);
		return meme;
	}
	
	// Get the schedule for the day at a current time (Long)
	public static List<Customer> schedule(double time) {
		List<Customer> schedule = new ArrayList<Customer>();
		Calendar then = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis((long) time);
		for (int i = db.getCustomers().size()-1; i >= 0; i--) {
			Customer me = db.getCustomers().get(i);
			then.setTimeInMillis((long) me.getLastCut());
			then.add(Calendar.DATE, me.getDaily());
			if (now.after(then) && me.getActive() == 1) {
				int last = 0;
				if (statementServices(me.getId()).size() != 0) {
					last = statementServices(me.getId()).get(0).getCost();
				}
				me.setLastPay(last);
				schedule.add(me);
			}
		}
		return schedule;
	}
	// Add new Customer to the database
	public static void addLawn(String fname, String lname, String address, String city, String state, int zip, int daily) {
		db.addCustomer(fname, lname, address, city, state, zip, daily);
	}
	// Login only allowed accounts
	public static Customer login(String name, String pass) {
		if (name.equals("MichaelTrupia") && pass.equals("Tjn%wE*jD6PHrXf5Pc*BuRd8&Ge@NBpanwDAHgFwh@x4W")) {
			System.out.println("User Connected");
			return db.getCustomers().get(0);
		} 
		return null;
	}
	// Get the services for a given customer
	public static List<Service> statementServices(int me) {
		List<Service> cuts = new ArrayList<Service>();
		for (int i = db.getServices().size()-1; i >= 0; i--) {
			Service a = db.getServices().get(i);
			if (me == a.getCustomerId()) {
				cuts.add(a);
			}
		}
		
		return cuts;
	}
	public static void setCustomerActive(int id, int active) {
		for (Customer me : Controller.getCustomes()) {
			if (me.getId() == id) {
				db.updateCustomers((long) me.getLastCut(), id, active);
			}
		}
	}
	// Get the payments for a given customer
	public static List<Payment> statementPayments(int me) {
		List<Payment> pays = new ArrayList<Payment>();
		for (int i = db.getPayments().size()-1; i >= 0; i--) {
			Payment a = db.getPayments().get(i);
			if (me == a.getCustomerId()) {
				pays.add(a);
			}
		}
		
		return pays;
	}
	// Add new service to the database
	public static void addService(int me, int cost, String memo) {
		db.addService(me, cost, memo);
		Calendar cuting = Calendar.getInstance();
		cuting.set(Calendar.HOUR_OF_DAY, 0);
		cuting.set(Calendar.MINUTE, 0);
		cuting.set(Calendar.SECOND, 0);
		cuting.set(Calendar.MILLISECOND, 0);
		db.updateCustomers(cuting.getTime().getTime(), me, db.getCustomers().get(me).getActive());
	}
	// Add new payment to the database
	public static void addPayment(int me, int paid, String memo) {
		db.addPayment(me, paid, memo);
	}
	// return customer id by address given
	public static int getCustomerIdByAddress(String address){
		for (Customer me : db.getCustomers()) {
			if (me.getAddress().equals(address)) {
				return me.getId();
			}
		}
		return -1;
	}
	// return customer that matches a search
	public static List<Customer> search(String term) {
		List<Customer> me = new ArrayList<Customer>();
		term = term.toLowerCase();
		for (Customer here : db.getCustomers()) {
			if (here.getFName().toLowerCase().contains(term) || here.getLName().toLowerCase().contains(term)|| here.getAddress().toLowerCase().contains(term)) {
				if (here.getId() != 0) {me.add(here);}
			}
		}
		
		return me;
	}
	
	public static void addToSchedule(int id){
		for (Customer me : db.getCustomers()) {
			if (me.getId()==id){
				db.updateCustomers(0, id, me.getActive());

			}
		}
	}
	
	public static List<Customer> getActiveCustomers() {
		List<Customer> hereo = new ArrayList<Customer>();
		for (int i = db.getCustomers().size()-1; i >= 0; i--) {
			if (db.getCustomers().get(i).getActive() == 1) {
				hereo.add(db.getCustomers().get(i));
			}
		}
		return hereo;
	}
	public static List<Customer> getDeactiveCustomers() {
		List<Customer> hereo = new ArrayList<Customer>();
		for (int i = db.getCustomers().size()-1; i > 0; i--) {
			if (db.getCustomers().get(i).getActive() == 0) {
				hereo.add(db.getCustomers().get(i));
			}
		}
		return hereo;
	}
	public static void deleteCustomer(int customerId) {
		db.removeCustomer(customerId);
	}
	public static void setCustomerDaily(int me, int daily){
		for (Customer meme : Controller.getCustomes()) {
			if (meme.getId() == me) {
				db.updateCustomerDaily(daily, me);
			}
		}
		
	}
	public static void removeSection(int id, int section, int customer) {
		if (section == 0) {
			db.removeService(id);
		} else {
			db.removePayment(id);
		}
	}
}