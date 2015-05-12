package Personal_Project.model;

import java.util.Date;


/* Payment class:
 * customer_id - Id of customer who payed
 * payed - payment of customer
 * comment - What was payed, how, etc.
 * date - Date of payment
 * 
 * Contains:
 * Payment() - sets Payment values to default
 * Payment(VALUES) - set Payment values to VALUES
 * gets and setters for all values
 */
public class Payment {
	private int id, customer_id, paid;
	private String comment;
	private long date;
	
	public Payment() {
	}
	
	public Payment(int id, int customer_id, int paid, String comment, long date) {
		this.id = id;
		this.customer_id = customer_id;
		this.paid = paid;
		this.comment = comment;
		this.date = date;
	}
	
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setCustomerId(int customer_id) { this.customer_id = customer_id; }
	public int getCustomerId() { return customer_id; }
	
	public void setPaid(int paid) { this.paid = paid; }
	public int getPaid() { return paid; }
	
	public void setComment(String comment) { this.comment = comment; }
	public String getComment() { return comment; }
	
	public void setDate(long date) { this.date = date; }
	public long getDate() { return date; }
	
	public String getDateString() {
		Date time = new Date();
		time.setTime(date);
		return time.toString().substring(0, 10);
	}
}
