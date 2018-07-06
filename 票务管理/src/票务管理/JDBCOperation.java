package ∆±ŒÒπ‹¿Ì;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.*;

public class JDBCOperation {
	private static Connection getConnection() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/ticket_system";
		String username = "root";
		String password = "";
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = (Connection) DriverManager.getConnection(url, username,
					password);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
