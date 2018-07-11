package ∆±ŒÒπ‹¿Ì;

public final class TicketManagementSystem {
	private static TicketInterface ticketInterface = new TicketInterface();
	public static void main(String[] args) {
		while (true) {
			if (!ticketInterface.menu())
				break;
		}

	}

}
