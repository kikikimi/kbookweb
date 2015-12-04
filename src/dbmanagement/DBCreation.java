/* Kimberly Disher
 * CIS 35B
 * Lab 6, database operations
 * Note this database was created on mySQL, and syntax has not been checked for compatibility with all databases.
 */
package dbmanagement;

import java.sql.*;
import util.FileIO;

public class DBCreation extends DBBaseOpts{
	public DBCreation () {
		super ();
		try {
			if (conn == null || conn.isClosed()){
				System.err.println ("Please ensure that the database kbook exists");
			}
			this.results = this.statement.executeQuery("show tables;");
			StringBuilder tableSB = new StringBuilder ();
			while (results.next()) {
				tableSB.append("'");
				tableSB.append(results.getString(1));
				tableSB.append("', ");
			}
			if (tableSB.indexOf("'model'") == -1 || tableSB.indexOf("'optionset'") == -1 && tableSB.indexOf("'opts'") == -1) {
				System.err.println ("Trying to create tables");
				createTables();
			}
			
		}
		catch (SQLException e) { System.err.println (e.getMessage()); }
	}
	//The data in the opts table is dependent on the data in both optionset and model, so if there is data there, we'll return true
	public boolean dataExists () {
		boolean hasdata = false;
		try {
			String sqlStr = "select count(*) from opts;";
			this.results = this.statement.executeQuery(sqlStr);
			this.results.next();
			hasdata = this.results.getInt(1) > 0 ? true : false;
		}
		catch (SQLException e) {System.err.println("Cannot confirm data in database kbook. " + e.getMessage());}
		return hasdata;
	}
	private void createTables () {
		String [] createTableStrs = FileIO.readLines("sqlscripts/createTables.sql");
		try {
			for(String sqlStr : createTableStrs) {
				statement.execute(sqlStr);
			}
		}
		catch (SQLException e) { System.err.println(e.getMessage());}
	}
}
