package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ticket-comments")
public class TicketChangesListDto {

	private List<TicketChangesDto> ticketChanges = new ArrayList<TicketChangesDto>();

	@XmlElement(name="ticket-comment")
	public List<TicketChangesDto> getTicketChanges() {
		return ticketChanges;
	}

	public void setTicketChanges(List<TicketChangesDto> ticketChanges) {
		this.ticketChanges = ticketChanges;
	}

}
