package Æ±Îñ¹ÜÀí;

import java.sql.Timestamp;

public class Order {
	private int oid;
	private int cid;
	private int bid;
	private int number;
	private Timestamp order_time;
	
	public Order(int tOid, int tCid, int tBid, int tNumber,
			Timestamp tOrder_time) {
		oid = tOid;
		cid = tCid;
		bid = tBid;
		number = tNumber;
		order_time = tOrder_time;
	}
	
	public int getOid() {
		return oid;
	}
	public int getCid() {
		return cid;
	}
	public int getBid() {
		return bid;
	}
	public int getNumber() {
		return number;
	}
	public Timestamp getOrder_time() {
		return order_time;
	}
	
	public void setOid(int tOid) {
		oid = tOid;
	}
	public void setCid(int tCid) {
		cid = tCid;
	}
	public void setBid(int tBid) {
		bid = tBid;
	}
	public void setNumber(int tNumber) {
		number = tNumber;
	}
	public void setOrder_time(Timestamp tOrder_time) {
		order_time = tOrder_time;
	}
}
