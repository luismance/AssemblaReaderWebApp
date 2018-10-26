package com.webdrone.assembla.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.webdrone.model.Ticket;

@XmlRootElement(name = "tickets")
public class TicketListAssemblaDto {

	private List<TicketAssemblaDto> tickets = new ArrayList<TicketAssemblaDto>();

	@XmlElement(name = "ticket")
	public List<TicketAssemblaDto> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketAssemblaDto> tickets) {
		this.tickets = tickets;
	}

	public TicketListAssemblaDto() {
	}

	public TicketListAssemblaDto(List<Ticket> tickets) {
		List<TicketAssemblaDto> result = new ArrayList<TicketAssemblaDto>();
		for (Ticket ticket : tickets) {
			TicketAssemblaDto tad = new TicketAssemblaDto();
			tad.setAssignedToId(ticket.getAssignedTo().getExternalRefId());
			tad.setAsssignedToName(ticket.getAssignedTo().getName());
			tad.setCompletedDate(new DateTime(ticket.getCompletedDate()));
			tad.setCreatedOn(new DateTime(ticket.getRemotelyCreated()));
			tad.setDescription(ticket.getDescription());
			tad.setMilestoneId(ticket.getMilestone().getExternalRefId());
			tad.setNumber(ticket.getTicketNumber());
			tad.setReporterId(ticket.getReporter().getExternalRefId());
			tad.setStatus(ticket.getStatus());
			result.add(tad);
		}
		this.tickets = result;
	}

}
