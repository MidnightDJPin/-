package Ʊ�����;

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
		System.out.println("1.�û�ע��");
		System.out.println("2.��ѯ���");
		System.out.println("3.��Ʊ����");
		System.out.println("4.������ѯ");
		System.out.println("5.�û���Ʊ");
		System.out.println("6.�˳�ϵͳ");
		System.out.print("���������ѡ����һ��������");
		String pattern = "[1-6]";
		Scanner input = new Scanner(System.in);
		String select = input.nextLine();
		while (!Pattern.matches(pattern, select)) {
			System.out.print("����������������룺");
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
		System.out.print("������ע���û��������Ȳ��ó���30���ַ�����");
		String namePatten = "[\u4e00-\u9fa5\\w]{0,30}";
		String cname = input.nextLine();
		while (!Pattern.matches(namePatten, cname)) {
			System.out.print("�����û������Ϸ������������룺");
			cname = input.nextLine();
		}
		System.out.print("������ע�����ֻ����룺");
		String phonePatten = "^1[0-9]{10}$";
		String phone = input.nextLine();
		while (!Pattern.matches(phonePatten, phone)) {
			System.out.print("�����ֻ����벻�Ϸ������������룺");
			phone = input.nextLine();
		}
		Customer customer = customerService.register(cname, phone);
		if (customer == null)
			System.out.println("�û�ע��ʧ�ܣ�");
		else
			System.out.println("�û�ע��ɹ���cid=" + customer.getCid()
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
			System.out.println("û���ҵ����ϲ�ѯ�����İ����Ϣ");
		else {
			System.out.println("��α��\t������\tĿ�ĵ�\t����ʱ��\t\t\t����\tƱ��\t");
			for (Bus bus : buses) {
				System.out.println(bus.getBid() + "\t" + bus.getOrigin() + "\t" + bus.getDestination()
				+ "\t" + bus.getStart_time().toString().substring(0, 16)
				+ "\t" + bus.getRest_seats() + "\t" + bus.getPrice() + "\t");
			}
		}
	}
	public void busesQuery() {
		Scanner input = new Scanner(System.in);
		System.out.print("����������أ�");
		String origin = input.nextLine();
		System.out.print("����������أ�");
		String destination = input.nextLine();
		System.out.print("������������ڣ����ڸ�ʽ:yy-MM-dd,���������ֱ��enter��������");
		String start_timeString = input.nextLine();
		while (!checkTimeStampType(start_timeString)) {
			System.out.print("�����ʽ����ȷ�������������ڵ�ǰ");
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
		System.out.print("������Ҫ��Ʊ���û�cid:");
		String cidString = input.nextLine();
		while (!cidIsValid(cidString)) {
			System.out.print("�����ʽ����ȷ��cid�����ڣ����������룺");
			cidString = input.nextLine();
		}
		int cid = Integer.valueOf(cidString);
		System.out.print("������Ҫ��Ʊ�İ��bid:");
		String bidString = input.nextLine();
		while (!bidIsValid(bidString)) {
			System.out.print("�����ʽ����ȷ��bid�����ڣ����������룺");
			cidString = input.nextLine();
		}
		int bid = Integer.valueOf(bidString);
		System.out.print("������Ҫ��Ʊ������:");
		String numString = input.nextLine();
		while (!isNumeric(numString)) {
			System.out.print("��������ȷ��������");
			numString = input.nextLine();
		}
		int number = Integer.valueOf(numString);
		if (ticketService.buyTickets(cid, bid, number))
			System.out.println("��Ʊ�ɹ���");
		else
			System.out.println("��Ʊʧ�ܣ�");
//		input.close();
	}
	
	
	private void printOrder(ArrayList<Order> orders) {
		if (orders == null || orders.size() == 0)
			System.out.println("û���ҵ����ϲ�ѯ�����Ķ�����Ϣ��");
		else {
			System.out.println("�������\t�û����\t��α��\tƱ��\t����ʱ��\t");
			for (Order order : orders) {
				System.out.println(order.getOid() + "\t" + order.getCid() + "\t" + order.getBid() + "\t"
						+ order.getNumber() + "\t" + order.getOrder_time().toString().substring(0,16) + "\t");
			}
		}
	}
	public void orderQuery() {
		Scanner input = new Scanner(System.in);
		System.out.print("������Ҫ��ѯ�������û�cid:");
		String cidString = input.nextLine();
		while (!cidIsValid(cidString)) {
			System.out.print("�����ʽ����ȷ��cid�����ڣ����������룺");
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
		System.out.print("������Ҫ��Ʊ�Ķ���oid:");
		String oidString = input.nextLine();
		while (!oidIsValid(oidString)) {
			System.out.print("�����ʽ����ȷ��oid�����ڣ����������룺");
			oidString = input.nextLine();
		}
		int oid = Integer.valueOf(oidString);
		ticketService.refund(oid);
		System.out.println("��Ʊ�ɹ���");
//		input.close();
	}
}
