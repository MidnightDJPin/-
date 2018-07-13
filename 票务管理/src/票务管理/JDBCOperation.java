package ∆±ŒÒπ‹¿Ì;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.mysql.jdbc.*;

public class JDBCOperation {
	public static Connection getConnection() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tickets_system?useUnicode=true&characterEncoding=utf-8&useSSL=true";
		String username = "root";
		String password = "";
		Connection connection = null;
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	public static int insert(Customer customer) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "insert into customers (cid, cname, phone) values(?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setInt(1, customer.getCid());
			pstmt.setString(2, customer.getCname());
			pstmt.setString(3, customer.getPhone());
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;		
	}
	public static int insert(Bus bus) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "insert into buses (bid, origin, destination, start_time, "
				+ "rest_seats, total_seats, price) values(?,?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setInt(1, bus.getBid());
			pstmt.setString(2, bus.getOrigin());
			pstmt.setString(3, bus.getDestination());
			pstmt.setTimestamp(4, bus.getStart_time());
			pstmt.setInt(5, bus.getRest_seats());
			pstmt.setInt(6, bus.getTotal_seats());
			pstmt.setInt(7, bus.getPrice());
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int insert(Order order) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "insert into orders (oid, cid, bid, number, order_time) "
				+ "values(?,?,?,?,?)";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setInt(1, order.getOid());
			pstmt.setInt(2, order.getCid());
			pstmt.setInt(3, order.getBid());
			pstmt.setInt(4, order.getNumber());
			pstmt.setTimestamp(5, order.getOrder_time());
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static int update(Customer customer) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "update customers set "
				+ "cname='" + customer.getCname() +"', " 
				+ "phone='" + customer.getPhone() + "' "
				+ "where cid=" + customer.getCid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int update(Bus bus) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "update buses set "
				+ "origin='" + bus.getOrigin() + "',"
				+ "destination='" + bus.getDestination() + "',"
				+ "start_time='" + bus.getStart_time() + "',"
				+ "rest_seats=" + bus.getRest_seats() + ","
				+ "total_seats=" + bus.getTotal_seats() + ","
				+ "price=" + bus.getPrice() + " "
				+ "where bid=" + bus.getBid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int update(Order order) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "update orders set "
				+ "cid=" + order.getCid() + ", "
				+ "bid=" + order.getBid() + ", "
				+ "number=" + order.getNumber() +" "
				+ "where oid=" + order.getOid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static int delete(Customer customer) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "delete from customers where cid=" + customer.getCid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int delete(Bus bus) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "delete from buses where cid=" + bus.getBid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	public static int delete(Order order) {
		Connection connection = getConnection();
		int i = 0;
		String sql = "delete from orders where oid=" + order.getOid() + "";
		PreparedStatement pstmt;
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			i = pstmt.executeUpdate();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static ArrayList<Customer> customersQuery() {
		Connection connection = getConnection();
		String sql = "select * from customers";
		PreparedStatement pstmt;
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				int cid = result.getInt("cid");
				String cname = result.getString("cname");
				String phone = result.getString("phone");
				customers.add(new Customer(cid, cname, phone));
			}
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}
	public static ArrayList<Bus> busesQuery() {
		Connection connection = getConnection();
		String sql = "select * from buses";
		PreparedStatement pstmt;
		ArrayList<Bus> buses = new ArrayList<>();
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				int bid = result.getInt("bid");
				String origin = result.getString("origin");
				String destination = result.getString("destination");
				Timestamp start_time = result.getTimestamp("start_time");
				int rest_seats = result.getInt("rest_seats");
				int total_seats = result.getInt("total_seats");
				int price = result.getInt("price");
				buses.add(new Bus(bid, origin, destination, start_time, rest_seats, total_seats, price));
			}
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buses;
	}
	public static ArrayList<Order> ordersQuery() {
		Connection connection = getConnection();
		String sql = "select * from orders";
		PreparedStatement pstmt;
		ArrayList<Order> orders = new ArrayList<>();
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet result = pstmt.executeQuery();
			while (result.next()) {
				int oid = result.getInt("oid");
				int cid = result.getInt("cid");
				int bid =result.getInt("bid");
				int number = result.getInt("number");
				Timestamp order_time = result.getTimestamp("order_time");
				orders.add(new Order(oid, cid, bid, number, order_time));
			}
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
}
