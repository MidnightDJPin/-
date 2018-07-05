package ∆±ŒÒπ‹¿Ì;

public class Customer {
	private int cid;
	private String cname;
	private String phone;
	
	public Customer(int id, String name, String phoneNumber) {
		cid = id;
		cname = name;
		phone = phoneNumber;
	}
	
	public int getCid() {
		return cid;
	}
	public String getCname() {
		return cname;
	}
	public String getPhone() {
		return phone;
	}
	
	public void setCid(int id) {
		cid = id;
	}
	public void setCname(String name) {
		cname = name;
	}
	public void setPhone(String phoneNumber) {
		phone = phoneNumber;
	}
	
}
