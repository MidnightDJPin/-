package 票务管理;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;


public class TicketService {
	private static TicketService instance = null;
	private int lastOid;
	
	
	public TicketService getInstance() {
		if (instance == null)
			instance = new TicketService();
		return instance;
	}
	
	public TicketService() {
		ArrayList<Order> orders = JDBCOperation.ordersQuery();
		if (orders == null) 
			lastOid = 0;
		else
			lastOid = orders.get(orders.size() - 1).getOid();
	}
	
	
	public boolean checkBidExist(int bid) {
		ArrayList<Bus> buses = JDBCOperation.busesQuery();
		boolean exist = false;
		for (Bus bus : buses) {
			if (bus.getBid() == bid)
				exist = true;
		}
		return exist;
	}
	public boolean checkOidExist(int oid) {
		ArrayList<Order> orders = JDBCOperation.ordersQuery();
		boolean exist = false;
		for (Order order :orders) {
			if (order.getOid() == oid)
				exist = true;
		}
		return exist;
	}
	
	
	public ArrayList<Bus> busesQuery(String origin, String destination, Timestamp start_time) {
		ArrayList<Bus> buses = JDBCOperation.busesQuery();
		Iterator<Bus> it = buses.iterator();
		while (it.hasNext()) {
			Bus bus = it.next();
			if (!(origin.equals(bus.getOrigin()) && destination.equals(bus.getDestination())
					&& bus.getStart_time().after(start_time)))
				it.remove();
		}
		return buses;
	}

	public ArrayList<Order> ordersQuery(int cid) {
		ArrayList<Order> orders = JDBCOperation.ordersQuery();
		Iterator<Order> it= orders.iterator();
		while (it.hasNext()) {
			Order order = it.next();
			if (order.getCid() != cid)
				it.remove();
		}
		return orders;
	}
	
	
	public boolean buyTickets(int cid, int bid, int number) {
		ArrayList<Bus> buses = JDBCOperation.busesQuery();
		Bus bus = buses.get(0);
		for (int i = 0; i < buses.size(); i++) {
			if (buses.get(i).getBid() == bid) {
				bus = buses.get(i);
				break;
			}
		}
		if (bus.getRest_seats() - number < 0) {
			System.out.println("余座不足！");
			return false;
		}
		else {
			bus.setRest_seats(bus.getRest_seats() - number);
			JDBCOperation.update(bus);
			JDBCOperation.insert(new Order(++lastOid, cid, bid, number, null));
			System.out.print("订单号："+ lastOid + ",车次编号为" + bid + "的班车");
			return true;
		}
	}
	public void refund(int oid) {
		ArrayList<Order> orders = JDBCOperation.ordersQuery();
		Order order = orders.get(0);
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getOid() == oid) {
				order = orders.get(i);
				break;
			}
		}
		JDBCOperation.delete(order);
		ArrayList<Bus> buses = JDBCOperation.busesQuery();
		Bus bus = buses.get(0);
		for (int i = 0; i < buses.size(); i++) {
			if (buses.get(i).getBid() == order.getBid()) {
				bus = buses.get(i);
				break;
			}
		}
		bus.setRest_seats(bus.getRest_seats() + order.getNumber());
		JDBCOperation.update(bus);
	}
	
}
