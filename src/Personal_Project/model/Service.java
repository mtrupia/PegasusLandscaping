package Personal_Project.model;

import java.util.Date;

/* Service class:
 * customer_id - Id of customer which service was done to.
 * cost - Cost of the service
 * comment - What service was done
 * date - Date of service
 * 
 * Contains:
 * Service() - sets Service values to default
 * Service(VALUES) - set Service values to VALUES
 * gets and setters for all values
 */
public class Service {
	private int id, customer_id, cost;
	private String comment;
	private long date;
	
	public Service() {
	}
	
	public Service(int id, int customer_id, int cost, String comment, long date) {
		this.id = id;
		this.customer_id = customer_id;
		this.cost = cost;
		this.comment = comment;
		this.date = date;
	}
	
	public void setId(int id) { this.id = id; }
	public int getId() { return id; }
	
	public void setCustomerId(int customer_id) { this.customer_id = customer_id; }
	public int getCustomerId() { return customer_id; }
	
	public void setCost(int cost) { this.cost = cost; }
	public int getCost() { return cost; }
	
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
