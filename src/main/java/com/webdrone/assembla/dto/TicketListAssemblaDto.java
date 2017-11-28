package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tickets")
public class TicketListAssemblaDto {

	private List<TicketAssemblaDto> tickets = new ArrayList<TicketAssemblaDto>();

	@XmlElement(name="ticket")
	public List<TicketAssemblaDto> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketAssemblaDto> tickets) {
		this.tickets = tickets;
	}

}
