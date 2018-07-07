package ∆±ŒÒπ‹¿Ì;

import java.util.ArrayList;

public class CustomService {
	private int lastCid;
	
	public CustomService() {
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
	
	
	public void register(String cname, String phone) {
		JDBCOperation.insert(new Customer(lastCid++, cname, phone));
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
