package com.webdrone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class RemoteEntity extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "EXTERNAL_REF_ID", length = 255, nullable = false)
	private String externalRefId;

	@Column(name = "REMOTELY_CREATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date remotelyCreated = new Date();

	@Column(name = "REMOTELY_CREATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date remotelyUpdated = new Date();

	public RemoteEntity(){
		super();
	}
	
	public String getExternalRefId() {
		return externalRefId;
	}

	public void setExternalRefId(String externalRefId) {
		this.externalRefId = externalRefId;
	}

	public Date getRemotelyCreated() {
		return remotelyCreated;
	}

	public void setRemotelyCreated(Date remotelyCreated) {
		this.remotelyCreated = remotelyCreated;
	}

	public Date getRemotelyUpdated() {
		return remotelyUpdated;
	}

	public void setRemotelyUpdated(Date remotelyUpdated) {
		this.remotelyUpdated = remotelyUpdated;
	}

}
