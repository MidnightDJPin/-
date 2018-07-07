package ∆±ŒÒπ‹¿Ì;

import java.util.ArrayList;

public class CustomService {
	private static CustomService instance;
	private int lastCid;
	
	public static CustomService getInstance() {
		if (instance == null)
			instance = new CustomService();
		return instance;
	}
	
	public CustomService() {
		ArrayList<Customer> customers = JDBCOperation.customersQuery();
		if (customers == null) 
			lastCid = 0;
		else
			lastCid = customers.get(customers.size() - 1).getCid();
	}
	
	
	public Customer register(String cname, String phone) {
		Customer customer = new Customer(lastCid++, cname, phone);
		JDBCOperation.insert(customer);
		return customer;
	}
	
	
	public void updateCustomer(int cid, String cname, String phone) {
		JDBCOperation.update(new Customer(cid, cname, phone));
	}
	
	
	public void daleteCustomer(int cid) {
		if (cid == lastCid)
			lastCid--;
		JDBCOperation.delete(new Customer(cid, null, null));
	}
}
