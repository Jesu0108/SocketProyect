package dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Dbm {
	private static Connection conn = null;

public static void openConnectionMySQL() throws SQLException {
		
		String DB_HOST = "localhost";
		String DB_PORT = "3306";
		String DB_SID = "medac";
		String DB_USER = "Medac";
		String DB_PASS = "";
		
		String db_url = "jdbc:mysql://" + DB_HOST + ":" +DB_PORT + "/" +DB_SID + "?user=" +DB_USER+ "&password=" + DB_PASS;
		conn = DriverManager.getConnection(db_url);
		
	}

public static CachedRowSet ExecuteQuery(String strQuery) throws SQLException {
	
	ResultSet r = conn.createStatement().executeQuery(strQuery);
	CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
	crs.populate(r);
	
	return crs;
}

public static void closeConnection() throws SQLException {
	conn.close();
	
	
}
	
}
