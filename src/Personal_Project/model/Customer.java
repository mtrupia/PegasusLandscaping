package Personal_Project.model;

/* Customer class:
 * id - easy ordering
 * fname - First name of the customer
 * lname - Last name of the customer
 * address - Contains house digits and street
 * city - City customer is in
 * state - State customer is in
 * zip - Zip code of customer
 * owed - Customer current amount owed
 * payed - Customer current amount payed
 * balance - Customer current balance
 * daily - Customer current segment for getting a lawn cut
 * lastCut - Customer's last lawn cut date
 * active - Customers current active status
 * 
 * Contains:
 * Customer() - sets Customer values to default
 * Customer(VALUES) - set Customer values to VALUES
 * gets and setters for all values
 */
public class Customer {
	private int id, zip, owed, paid, balance, daily, active;
	private String fname, lname, address, city, state;
	private double lastCut;
	private int lastPay;
	
	public Customer() {
	}
	
	public Customer(int id, String fname, String lname, String address, String city, String state, int zip, 
	int owed, int paid, int balance, int daily, double lastCut, int active) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.owed = owed;
		this.paid = paid;
		this.balance = balance;
		this.daily = daily;
		this.lastCut = lastCut;
		this.active = active;
	}
	
	public void setLastPay(int lastPay) { this.lastPay = lastPay; }
	public int getLastPay() { return lastPay; }
	
	public void setActive(int active) { this.active = active; }
	public int getActive() { return active; }
	
	public void setLastCut(double lastCut) { this.lastCut = lastCut; }
	public double getLastCut() { return lastCut; }
	
	public void setDaily(int daily) { this.daily = daily; }
	public int getDaily() { return daily; }
	
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setFName(String fname) { this.fname = fname; }
	public String getFName() { return fname; }
	
	public void setLName(String lname) { this.lname = lname; }
	public String getLName() { return lname; }
	
	public void setAddress(String address) { this.address = address; }
	public String getAddress() { return address; }
	
	public void setCity(String city) { this.city = city; }
	public String getCity() { return city; }
	
	public void setState(String state) { this.state = state; }
	public String getState() { return state; }
	
	public void setZip(int zip) { this.zip = zip; }
	public int getZip() { return zip; }
	
	public void setOwed(int owed) { this.owed = owed; }
	public int getOwed() { return owed; }
	
	public void setPaid(int paid) { this.paid = paid; }
	public int getPaid() { return paid; }
	
	public void setBalance(int balance) { this.balance = balance; }
	public int getBalance() { return balance; }
}
