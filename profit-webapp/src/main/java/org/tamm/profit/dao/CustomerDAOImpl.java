package org.tamm.profit.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.tamm.profit.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {

	private Connection dbConnection;
	private static CustomerDAOImpl db;
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:test";//in-memory, persistent until next restart
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    private static String DB_TABLE = "CUSTOMER";
    private static final String TEST_DB_TABLE = "TEST_CUSTOMER";
    
    /**
     * For testing
     */
    public void setTblName()
    {
    	DB_TABLE = TEST_DB_TABLE;
    }
    
    private CustomerDAOImpl(){
    	try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            dbConnection.setAutoCommit(false);
            init();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Get the singleton db-connection object
     * @return
     */
    public static CustomerDAOImpl getDbCon()
    {
    	if (db == null) {
            db = new CustomerDAOImpl();
        }
        return db;
    }
    
    /**
     * Initialize the database and create db-table
     * @throws SQLException
     */
    public void init() throws SQLException
    {
    	DatabaseMetaData dbm = dbConnection.getMetaData();
    	Statement stmt = dbConnection.createStatement();
    	
    	//stmt.execute("DROP TABLE "+DB_TABLE);//for testing only!
    	
		// check if "student" table is there
		ResultSet tables = dbm.getTables(null, null, DB_TABLE, null);
		if (tables.next() == false) {
			stmt.execute("CREATE TABLE "+DB_TABLE+" (id identity primary key, "
			+ "firstname varchar(255), "
			+ "lastname varchar(255), "
			+ "dateOfBirth date, "
			+ "username varchar(255), "
			+ "password varchar(255))");
		}
		
		stmt.close();
		dbConnection.commit();
    }
	
	@Override
	public void addCustomer(Customer customer) {
		String insertQuery = "INSERT INTO "+DB_TABLE+"(firstname, lastname, dateOfBirth, username, password) values (?,?,?,?,?)";
		try
		{
			PreparedStatement stmt = dbConnection.prepareStatement(insertQuery);

			stmt.setString(1, customer.getFirstname());
			stmt.setString(2, customer.getLastname());
			stmt.setString(3, customer.getDateOfBirth());
			stmt.setString(4, customer.getUsername());
			stmt.setString(5, customer.getPassword());
			
			stmt.execute();
			stmt.close();
			dbConnection.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateCustomer(Customer customer) {
		String updateQuery = "UPDATE "+DB_TABLE+" SET firstname=?, lastname=?, dateOfBirth=?, username=?, password=? WHERE id = ?";
		try
		{
			PreparedStatement stmt = dbConnection.prepareStatement(updateQuery);
			stmt.setString(1, customer.getFirstname());
			stmt.setString(2, customer.getLastname());
			stmt.setString(3, customer.getDateOfBirth());
			stmt.setString(4, customer.getUsername());
			stmt.setString(5, customer.getPassword());
			stmt.setInt(6, customer.getId());
			stmt.execute();
			stmt.close();
			dbConnection.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}	

	}

	@Override
	public List<Customer> listCustomers() {
		String listQuery = "SELECT * FROM "+DB_TABLE+" ORDER BY id";
		List<Customer> customers = new ArrayList<Customer>();
		
		try {
			Statement stmt = dbConnection.createStatement();
			ResultSet res = stmt.executeQuery(listQuery);
			
			while(res.next())
			{
				Customer customer = new Customer();
				customer.setFirstname(res.getString("firstname"));
				customer.setLastname(res.getString("lastname"));
				customer.setDateOfBirth(res.getString("dateOfBirth"));
				customer.setUsername(res.getString("username"));
				customer.setPassword(res.getString("password"));
				customer.setId(res.getInt("id"));
				
				customers.add(customer);
			}
			stmt.close();
			dbConnection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(int id) {
		String getQuery = "SELECT * FROM "+DB_TABLE+" WHERE id=?";
		Customer customer = null;
		try {
			PreparedStatement stmt = dbConnection.prepareStatement(getQuery);
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			
			if(res.next())
			{
				customer = new Customer();
				customer.setFirstname(res.getString("firstname"));
				customer.setLastname(res.getString("lastname"));
				customer.setDateOfBirth(res.getString("dateOfBirth"));
				customer.setUsername(res.getString("username"));
				customer.setId(res.getInt("id"));
				customer.setPassword(res.getString("password"));
			}
			
			stmt.close();
			dbConnection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
	}

	@Override
	public void removeCustomer(int id) {
		String removeQuery = "DELETE FROM "+DB_TABLE+" WHERE id=?";
		try {
			PreparedStatement stmt = dbConnection.prepareStatement(removeQuery);
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
			dbConnection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Customer getCustomerByUsername(String username) {
		Customer customer = null;
		String selectQuery = "SELECT * FROM "+DB_TABLE+" WHERE username = ?";
		try
		{
			PreparedStatement stmt = dbConnection.prepareStatement(selectQuery);
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();
			if(res.next())
			{
				customer = new Customer();
				customer.setFirstname(res.getString("firstname"));
				customer.setLastname(res.getString("lastname"));
				customer.setDateOfBirth(res.getString("dateOfBirth"));
				customer.setUsername(res.getString("username"));
				customer.setId(res.getInt("id"));
				customer.setPassword(res.getString("password"));
			}
			stmt.close();
			dbConnection.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return customer;
	}
}
