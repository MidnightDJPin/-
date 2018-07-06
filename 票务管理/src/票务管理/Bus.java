package ∆±ŒÒπ‹¿Ì;

import java.sql.Timestamp;



public class Bus {
	private int bid;
	private String origin;
	private String destination;
	private Timestamp start_time;
	private int rest_seats;
	private int total_seats;
	private int price;
	
	public Bus(int id, String tOrigin, String tDestination, Timestamp tStart_time,
			int tRest_seats, int tTotal_seats, int tPrice) {
		bid = id;
		origin = tOrigin;
		destination = tDestination;
		start_time = tStart_time;
		rest_seats = tRest_seats;
		total_seats = tTotal_seats;
		price = tPrice;
	}
	
	public int getBid() {
		return bid;
	}
	public String getOrigin() {
		return origin;
	}
	public String getDestination() {
		return destination;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public int getRest_seats() {
		return rest_seats;
	}
	public int getTotal_seats() {
		return total_seats;
	}
	public int getPrice() {
		return price;
	}
	
	public void setBid(int id) {
		bid = id;
	}
	public void setOrigin(String tOrigin) {
		origin = tOrigin;
	}
	public void setDestination(String tDestination) {
		destination = tDestination;
	}
	public void setStart_time(Timestamp tStart_time) {
		start_time = tStart_time;
	}
	public void setRest_seats(int tRest_seats) {
		rest_seats = tRest_seats;
	}
	public void setTotal_seats(int tTotal_seats) {
		total_seats = tTotal_seats;
	}
}
