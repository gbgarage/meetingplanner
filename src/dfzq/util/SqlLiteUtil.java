package dfzq.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlLiteUtil {
	public static Connection getConn(String dbFile) throws Exception {
		Connection conn = null;
    	Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection( "jdbc:sqlite:/"+dbFile);
        return conn;
	}

	public static void closeConn(Connection conn) throws Exception {
		if(conn!=null){
			conn.close();
		}
	}
}
