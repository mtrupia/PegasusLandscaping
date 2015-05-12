package Personal_Project.persist;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Personal_Project.model.*;

public class InitialData {
	public static List<Customer> getCustomers() throws IOException, ParseException {
		List<Customer> customerList = new ArrayList<Customer>();
		ReadCSV readCustomer = new ReadCSV("customers.csv");
		try {
			while (true) {
				List<String> tuple = readCustomer.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Customer customer = new Customer();
				customer.setId(Integer.parseInt(i.next()));
				customer.setFName(i.next());
				customer.setLName(i.next());
				customer.setAddress(i.next());
				customer.setCity(i.next());
				customer.setState(i.next());
				customer.setZip(Integer.parseInt(i.next()));
				customer.setOwed(Integer.parseInt(i.next()));
				customer.setPaid(Integer.parseInt(i.next()));
				customer.setBalance(Integer.parseInt(i.next()));
				customer.setDaily(Integer.parseInt(i.next()));
				customer.setLastCut(Double.parseDouble(i.next()));
				customer.setActive(Integer.parseInt(i.next()));
				customerList.add(customer);
			}
			return customerList;
		} finally {
			readCustomer.close();
		}
	}
	
	public static List<Payment> getPayments() throws IOException, ParseException {
		List<Payment> paymentList = new ArrayList<Payment>();
		ReadCSV readPayment = new ReadCSV("payments.csv");
		try {
			while (true) {
				List<String> tuple = readPayment.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Payment payment = new Payment();
				payment.setId(Integer.parseInt(i.next()));
				payment.setCustomerId(Integer.parseInt(i.next()));
				payment.setComment(i.next());
				payment.setPaid(Integer.parseInt(i.next()));
				payment.setDate(Long.parseLong(i.next()));
				paymentList.add(payment);
			}
			return paymentList;
		} finally {
			readPayment.close();
		}
	}
	
	public static List<Service> getServices() throws IOException, ParseException {
		List<Service> serviceList = new ArrayList<Service>();
		ReadCSV readService = new ReadCSV("services.csv");
		try {
			while (true) {
				List<String> tuple = readService.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Service service = new Service();
				service.setId(Integer.parseInt(i.next()));
				service.setCustomerId(Integer.parseInt(i.next()));
				service.setComment(i.next());
				service.setCost(Integer.parseInt(i.next()));
				service.setDate(Long.parseLong(i.next()));
				serviceList.add(service);
			}
			return serviceList;
		} finally {
			readService.close();
		}
	}
}
