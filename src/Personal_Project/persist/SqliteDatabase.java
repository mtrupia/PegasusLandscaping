package Personal_Project.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Personal_Project.model.*;

public class SqliteDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load sqlite driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}
	
	public List<Customer> getCustomers() {
		return executeTransaction(new Transaction<List<Customer>>() {
			@Override
			public List<Customer> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from customers"
					);
					
					List<Customer> result = new ArrayList<Customer>();
					
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Customer customer = new Customer();
						int index = 1;
						customer.setId(resultSet.getInt(index++));
						customer.setFName(resultSet.getString(index++));
						customer.setLName(resultSet.getString(index++));
						customer.setAddress(resultSet.getString(index++));
						customer.setCity(resultSet.getString(index++));
						customer.setState(resultSet.getString(index++));
						customer.setZip(resultSet.getInt(index++));
						customer.setOwed(resultSet.getInt(index++));
						customer.setPaid(resultSet.getInt(index++));
						customer.setBalance(resultSet.getInt(index++));
						customer.setDaily(resultSet.getInt(index++));
						customer.setLastCut(resultSet.getLong(index++));
						customer.setActive(resultSet.getInt(index++));
						result.add(customer);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public List<Service> getServices() {
		return executeTransaction(new Transaction<List<Service>>() {
			@Override
			public List<Service> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from services"
					);
					
					List<Service> result = new ArrayList<Service>();
					
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Service service = new Service();
						int index = 1;
						service.setId(resultSet.getInt(index++));
						service.setCustomerId(resultSet.getInt(index++));
						service.setComment(resultSet.getString(index++));
						service.setCost(resultSet.getInt(index++));
						service.setDate(resultSet.getLong(index++));
						result.add(service);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public List<Payment> getPayments() {
		return executeTransaction(new Transaction<List<Payment>>() {
			@Override
			public List<Payment> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				
				try {
					stmt = conn.prepareStatement(
							"select * from payments"
					);
					
					List<Payment> result = new ArrayList<Payment>();
					
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						Payment payment = new Payment();
						int index = 1;
						payment.setId(resultSet.getInt(index++));
						payment.setCustomerId(resultSet.getInt(index++));
						payment.setComment(resultSet.getString(index++));
						payment.setPaid(resultSet.getInt(index++));
						payment.setDate(resultSet.getLong(index++));
						result.add(payment);
					}
					
					return result;
				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void addCustomer(final String fname, final String lname, final String address, final String city, final String state, final int zip, final int daily) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into customers (fname, lname, address, city, state, zip, owed, paid, balance, daily, lastCut, active) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
					);
					
					stmt.setString(1, fname);
					stmt.setString(2, lname);
					stmt.setString(3, address);
					stmt.setString(4, city);
					stmt.setString(5, state);
					stmt.setInt(6, zip);
					stmt.setInt(7, 0);
					stmt.setInt(8, 0);
					stmt.setInt(9, 0);
					stmt.setInt(10, daily);
					stmt.setLong(11, 0);
					stmt.setInt(12, 1);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void addService(final int customerId, final int cost, final String comment) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into services (customer_id, comment, cost, date) values (?, ?, ?, ?)"
					);
					
					stmt.setInt(1, customerId);
					stmt.setString(2, comment);
					stmt.setInt(3, cost);
					stmt.setLong(4, new Date().getTime());
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void addPayment(final int customerId, final int paid, final String comment) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"insert into payments (customer_id, comment, paid, date) values (?, ?, ?, ?)"
					);
					
					stmt.setInt(1, customerId);
					stmt.setString(2, comment);
					stmt.setInt(3, paid);
					stmt.setLong(4, new Date().getTime());
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void updateCustomers(final long lastCut, final int customerId, final int active) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"update customers set lastCut=?, active=? where id=?"
					);
					
					stmt.setLong(1, lastCut);
					stmt.setInt(2, active);
					stmt.setInt(3, customerId);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void updateCustomerDaily(final int daily, final int customerId) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"update customers set daily=? where id=?"
					);
					
					stmt.setInt(1, daily);
					stmt.setInt(2, customerId);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void removePayment(final int id) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"delete from payments where id=?"
					);
					
					stmt.setLong(1, id);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void removeService(final int id) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"delete from services where id=?"
					);
					
					stmt.setLong(1, id);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void updateCustomerTotal(final int owed, final int paid, final int id) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				
				try {
					stmt = conn.prepareStatement(
							"update customers set owed=? paid=? where id=?"
					);
					
					stmt.setInt(1, owed);
					stmt.setInt(2, paid);
					stmt.setInt(3, id);
					
					stmt.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}
	
	public void removeCustomer(final int customerId) {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				
				try {
					stmt1 = conn.prepareStatement(
							"delete from customers where id=?"
					);
					stmt1.setInt(1, customerId);
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"delete from payments where customer_id=?"
					);
					stmt2.setInt(1, customerId);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"delete from services where customer_id=?"
					);
					stmt3.setInt(1, customerId);
					stmt3.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}
	
	public void backUpData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				Statement backup = null;
				
				try {
					backup = conn.createStatement();
					backup.executeUpdate("backup main to C:/Users/Public/backupPP.bak");
					
					return true;
				} finally {
					DBUtil.closeQuietly(backup);
				}
			}
		});
	}
	
	public void restoreData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				Statement restore = null;
				
				try {
					restore = conn.createStatement();
					restore.executeUpdate("restore main from C:/Users/Public/backupPP.bak");
					
					return true;
				} finally {
					DBUtil.closeQuietly(restore);
				}
			}
		});
	}
	
	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				PreparedStatement stmt5 = null;
				
				try {
					stmt1 = conn.prepareStatement(
							"create table customers (" +
							"    id integer primary key," +
							"    fname varchar(50)," +
							"    lname varchar(50)," +
							"    address varchar(100)," +
							"    city varchar(50)," +
							"    state varchar(10)," +
							"    zip integer," +
							"    owed integer," +
							"    paid integer," +
							"    balance integer," +
							"    daily integer," +
							"    lastCut bigint," +
							"    active integer" +
							")");
					stmt1.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table payments (" +
									"    id integer primary key," +
									"    customer_id integer," +
									"    comment varchar(500)," +
									"    paid integer," +
									"    date bigint" +
									")");
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table services (" +
									"    id integer primary key," +
									"    customer_id integer," +
									"    comment varchar(500)," +
									"    cost integer," +
									"    date bigint" +
									")");
					stmt4.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
					DBUtil.closeQuietly(stmt5);
				}
			}
		});
	}
	
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:sqlite:main.db");
		
		// Set autocommit to false to allow multiple the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Customer> customerList;
				List<Payment> paymentList;
				List<Service> serviceList;
				
				try {
					customerList = InitialData.getCustomers();
					paymentList = InitialData.getPayments();
					serviceList = InitialData.getServices();
				} catch (IOException | ParseException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertCustomer = null;
				PreparedStatement insertPayment = null;
				PreparedStatement insertService = null;

				try {
					insertCustomer = conn.prepareStatement("insert into customers values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
					for (Customer customer : customerList) {
						insertCustomer.setInt(1, customer.getId());
						insertCustomer.setString(2, customer.getFName());
						insertCustomer.setString(3, customer.getLName());
						insertCustomer.setString(4, customer.getAddress());
						insertCustomer.setString(5, customer.getCity());
						insertCustomer.setString(6, customer.getState());
						insertCustomer.setInt(7, customer.getZip());
						insertCustomer.setInt(8, customer.getOwed());
						insertCustomer.setInt(9, customer.getPaid());
						insertCustomer.setInt(10, customer.getBalance());
						insertCustomer.setInt(11, customer.getDaily());
						insertCustomer.setLong(12, (long) customer.getLastCut());
						insertCustomer.setInt(13, customer.getActive());
						insertCustomer.addBatch();
					}
					insertCustomer.executeBatch();
					
					insertPayment = conn.prepareStatement("insert into payments values (?, ?, ?, ?, ?)");
					for (Payment payment : paymentList) {
						insertPayment.setInt(1, payment.getId());
						insertPayment.setInt(2, payment.getCustomerId());
						insertPayment.setString(3, payment.getComment());
						insertPayment.setInt(4, payment.getPaid());
						insertPayment.setLong(5, payment.getDate());
						insertPayment.addBatch();
					}
					insertPayment.executeBatch();
					
					insertService = conn.prepareStatement("insert into services values (?, ?, ?, ?, ?)");
					for (Service service : serviceList) {
						insertService.setInt(1, service.getId());
						insertService.setInt(2, service.getCustomerId());
						insertService.setString(3, service.getComment());
						insertService.setInt(4, service.getCost());
						insertService.setLong(5, service.getDate());
						insertService.addBatch();
					}
					insertService.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertCustomer);
					DBUtil.closeQuietly(insertPayment);
					DBUtil.closeQuietly(insertService);
				}
			}
		});
	}
	
	// The main method creates the database tables and loads the initial data.
		public static void main(String[] args) throws IOException {
			System.out.println("Creating tables...");
			SqliteDatabase db = new SqliteDatabase();
			db.createTables();
			
			System.out.println("Loading initial data...");
			db.loadInitialData();
			
			System.out.println("Success!");
		}
}
