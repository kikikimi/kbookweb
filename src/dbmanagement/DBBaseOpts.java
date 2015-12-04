/* Kimberly Disher
 * CIS 35B
 * Lab 6, database operations
 * Note this database was created on mySQL, and syntax has not been checked for compatibility with all databases.
 */
package dbmanagement;

import java.sql.*;

public abstract class DBBaseOpts {
	protected static final String USER = "auto_user";
	protected static final String PASS = "focusztw";
	protected static final String JDBCDRIVER = "com.mysql.jdbc.Driver";
	protected static final String CONNSTR = "jdbc:mysql://localhost/kbook";
	
	protected static Connection conn;
	protected ResultSet results;
	protected Statement statement;
	protected String sqlString = "";

	DBBaseOpts () {
		try {
			Class.forName(JDBCDRIVER);	
		}
		catch (ClassNotFoundException e) {
			System.err.println("Mysql jdbc driver not found." + e.getMessage());
		}
		try {
		conn = DriverManager.getConnection(CONNSTR + "?user=" + USER + "&password=" + PASS);
		statement = conn.createStatement();
		}
		catch (SQLException se) {
			System.err.println("MySQL err:" + se.getMessage());
			
		}
	}
	
	public void close() {
		try{
			statement.close();
			conn.close();
		}
		catch (SQLException e) {
			System.err.println (e.getMessage());
		}	
	}
}
