package dbm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class Dbm {
	private static Connection conn = null;

public static void openConnectionMySQL() throws SQLException {
		
		String DB_HOST = "localhost";
		String DB_PORT = "3306";
		String DB_SID = "programacion";
		String DB_USER = "Medac";
		String DB_PASS = "";
		
		String db_url = "jdbc:mysql://" + DB_HOST + ":" +DB_PORT + "/" +DB_SID + "?user=" +DB_USER+ "&password=" + DB_PASS;
		conn = DriverManager.getConnection(db_url);
		
	}

public static void executeInsert(String strQuery) throws SQLException {
	
	Statement ordenSQL;
	
	ordenSQL = conn.createStatement();
	ordenSQL.executeUpdate(strQuery);
	ordenSQL.close();
}

public static void closeConnection() throws SQLException {
	conn.close();
	
	
}
public static ResultSet getData (String sql) {
	
	ResultSet resultado = null;
	
	Statement ordenSQL;
	try {
		ordenSQL = conn.createStatement();
		resultado = ordenSQL.executeQuery(sql);
	} catch (SQLException e) {
		System.out.println("No se ha podido ejecutar la orden : " +sql);
		e.printStackTrace();
	}

return resultado;
}
	
}
