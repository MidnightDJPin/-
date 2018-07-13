package ∆±ŒÒπ‹¿Ì;

import java.util.ArrayList;

public class CustomerService {
	private static CustomerService instance = null;
	private int lastCid;
	
	public CustomerService getInstance() {
		if (instance == null)
			instance = new CustomerService();
		return instance;
	}
	
	public CustomerService() {
		ArrayList<Customer> customers = JDBCOperation.customersQuery();
		if (customers == null) 
			lastCid = 0;
		else
			lastCid = customers.get(customers.size() - 1).getCid();
	}
	
	
	public boolean checkCidExist(int cid) {
		ArrayList<Customer> customers = JDBCOperation.customersQuery();
		boolean exist = false;
		for (Customer customer : customers) {
			if (customer.getCid() == cid)
				exist = true;
		}
		return exist;
	}
	
	
	public Customer register(String cname, String phone) {
		Customer customer = new Customer(++lastCid, cname, phone);
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
