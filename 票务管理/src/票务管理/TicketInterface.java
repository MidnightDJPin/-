package 票务管理;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;



public class TicketInterface {
	private CustomerService customerService;
	private TicketService ticketService;
	
	public TicketInterface() {
		customerService = new CustomerService();
		ticketService = new TicketService();
	}
	
	public boolean menu() {
		System.out.println("1.用户注册");
		System.out.println("2.查询班次");
		System.out.println("3.车票订购");
		System.out.println("4.订单查询");
		System.out.println("5.用户退票");
		System.out.println("6.退出系统");
		System.out.print("输入序号以选择下一步操作：");
		String pattern = "[1-6]";
		Scanner input = new Scanner(System.in);
		String select = input.nextLine();
		while (!Pattern.matches(pattern, select)) {
			System.out.print("输入错误，请重新输入：");
			select = input.nextLine();
		}
		int choose = Integer.parseInt(select);
		switch (choose) {
		case 1: {
			customerRegister();
			break;
		}
		case 2: {
			busesQuery();
			break;
		}
		case 3: {
			buyTickets();
			break;
		}
		case 4: {
			orderQuery();
			break;
		}
		case 5: {
			refund();
			break;
		}
		case 6: {
			input.close();
			return false;
		}
		}
//		input.close();
		return true;
	}


	
	public void customerRegister() {
		Scanner input = new Scanner(System.in);
		System.out.print("请输入注册用户名（长度不得超过30个字符）：");
		String namePatten = "[\u4e00-\u9fa5\\w]{0,30}";
		String cname = input.nextLine();
		while (!Pattern.matches(namePatten, cname)) {
			System.out.print("输入用户名不合法，请重新输入：");
			cname = input.nextLine();
		}
		System.out.print("请输入注册用手机号码：");
		String phonePatten = "^1[0-9]{10}$";
		String phone = input.nextLine();
		while (!Pattern.matches(phonePatten, phone)) {
			System.out.print("输入手机号码不合法，请重新输入：");
			phone = input.nextLine();
		}
		Customer customer = customerService.register(cname, phone);
		if (customer == null)
			System.out.println("用户注册失败！");
		else
			System.out.println("用户注册成功，cid=" + customer.getCid()
			+ " cname=" + customer.getCname() + " phone=" + customer.getPhone());
//		input.close();
	}
	
	
	private boolean checkTimeStampType(String string) {
		String timeStampPatten = "^(\\d{4}-\\d{2}-\\d{2})";
		if (string.equals("")) return true;
		else if (!Pattern.matches(timeStampPatten, string)) return false;
		else {
			string += " 00:00:00";
			Timestamp timestamp = Timestamp.valueOf(string);
			Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
			if (timestamp.getYear() < currentTimeStamp.getYear()) return false;
			else if (timestamp.getYear() > currentTimeStamp.getYear()) return true;
			else if (timestamp.getMonth() < currentTimeStamp.getMonth()) return false;
			else if (timestamp.getMonth() > currentTimeStamp.getMonth()) return true;
			else if (timestamp.getDay() < currentTimeStamp.getDay()) return false;
			else return true;
		}
	}
	private void printBuses(ArrayList<Bus> buses) {
		if (buses == null || buses.size() == 0)
			System.out.println("没有找到符合查询条件的班次信息");
		else {
			System.out.println("班次编号\t出发地\t目的地\t出发时间\t\t\t余座\t票价\t");
			for (Bus bus : buses) {
				System.out.println(bus.getBid() + "\t" + bus.getOrigin() + "\t" + bus.getDestination()
				+ "\t" + bus.getStart_time().toString().substring(0, 16)
				+ "\t" + bus.getRest_seats() + "\t" + bus.getPrice() + "\t");
			}
		}
	}
	public void busesQuery() {
		Scanner input = new Scanner(System.in);
		System.out.print("请输入出发地：");
		String origin = input.nextLine();
		System.out.print("请输入出发地：");
		String destination = input.nextLine();
		System.out.print("请输入出发日期（日期格式:yy-MM-dd,若无需求可直接enter跳过）：");
		String start_timeString = input.nextLine();
		while (!checkTimeStampType(start_timeString)) {
			System.out.print("输入格式不正确或输入日期早于当前");
			start_timeString = input.nextLine();
		}
		Timestamp start_time;
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		if (start_timeString.equals(""))
			start_time = Timestamp.valueOf(currentTimeStamp.toString());
		else {
			start_timeString += " 00:00:00";
			start_time = Timestamp.valueOf(start_timeString);
			if (start_time.before(currentTimeStamp)) {
				start_time.setHours(currentTimeStamp.getHours());
				start_time.setMinutes(currentTimeStamp.getMinutes() + 5);
			}
		}
		ArrayList<Bus> buses = ticketService.busesQuery(origin, destination, start_time);
		printBuses(buses);
//		input.close();
	}
	
	private boolean isNumeric(String numString) {
		String numPatten = "\\d{1,11}";
		return Pattern.matches(numPatten, numString);
	}
	private boolean cidIsValid(String cidString) {
		if (!isNumeric(cidString))
			return false;
		int tCid = Integer.valueOf(cidString);
		return customerService.checkCidExist(tCid);
	}
	private boolean bidIsValid(String bidString) {
		if (!isNumeric(bidString))
			return false;
		int tBid = Integer.valueOf(bidString);
		return ticketService.checkBidExist(tBid);
	}
	public void buyTickets() {
		Scanner input = new Scanner(System.in);
		System.out.print("请输入要购票的用户cid:");
		String cidString = input.nextLine();
		while (!cidIsValid(cidString)) {
			System.out.print("输入格式不正确或cid不存在，请重新输入：");
			cidString = input.nextLine();
		}
		int cid = Integer.valueOf(cidString);
		System.out.print("请输入要购票的班次bid:");
		String bidString = input.nextLine();
		while (!bidIsValid(bidString)) {
			System.out.print("输入格式不正确或bid不存在，请重新输入：");
			cidString = input.nextLine();
		}
		int bid = Integer.valueOf(bidString);
		System.out.print("请输入要购票的数量:");
		String numString = input.nextLine();
		while (!isNumeric(numString)) {
			System.out.print("请输入正确的数量：");
			numString = input.nextLine();
		}
		int number = Integer.valueOf(numString);
		if (ticketService.buyTickets(cid, bid, number))
			System.out.println("购票成功！");
		else
			System.out.println("购票失败！");
//		input.close();
	}
	
	
	private void printOrder(ArrayList<Order> orders) {
		if (orders == null || orders.size() == 0)
			System.out.println("没有找到符合查询条件的订单信息！");
		else {
			System.out.println("订单编号\t用户编号\t班次编号\t票数\t操作时间\t");
			for (Order order : orders) {
				System.out.println(order.getOid() + "\t" + order.getCid() + "\t" + order.getBid() + "\t"
						+ order.getNumber() + "\t" + order.getOrder_time().toString().substring(0,16) + "\t");
			}
		}
	}
	public void orderQuery() {
		Scanner input = new Scanner(System.in);
		System.out.print("请输入要查询订单的用户cid:");
		String cidString = input.nextLine();
		while (!cidIsValid(cidString)) {
			System.out.print("输入格式不正确或cid不存在，请重新输入：");
			cidString = input.nextLine();
		}
		int cid = Integer.valueOf(cidString);
		ArrayList<Order> orders = ticketService.ordersQuery(cid);
		printOrder(orders);
//		input.close();
	}
	
	private boolean oidIsValid(String oidString) {
		if (!isNumeric(oidString))
			return false;
		int tOid = Integer.valueOf(oidString);
		return ticketService.checkOidExist(tOid);
	}
	public void refund() {
		Scanner input = new Scanner(System.in);
		System.out.print("请输入要退票的订单oid:");
		String oidString = input.nextLine();
		while (!oidIsValid(oidString)) {
			System.out.print("输入格式不正确或oid不存在，请重新输入：");
			oidString = input.nextLine();
		}
		int oid = Integer.valueOf(oidString);
		ticketService.refund(oid);
		System.out.println("退票成功！");
//		input.close();
	}
}
