package com.nikapps.phinppatk.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="ppatk_result")
public class PpatkResultEntity {
	
	public static final String getIdentificationNumber = null;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	
	@Column(name="version", columnDefinition = "integer default 25" ) private Long version;
	
	
	@Column(name = "message")
	private String message;
    
	@CreationTimestamp
    @Column(name="date_created")
	private Date dateCreated;
    
	@UpdateTimestamp
	@Column(name="last_updated") 
	private Date lastUpdated;
	 
    //settonulldefault
	@Column(name = "online_registration_id") 
	private String onlineRegistrationId;
	  
	@Column(name = "registration_id") 
	private String registrationId;
	    
	
	@Column(name = "created_by_id", columnDefinition = "integer default 117") 
	private String createdById="117";
	 
	@Column(name = "status") 
	private String status;
	
	 @Column(name = "identification_number") 
	 private String identificationNumber;
		  
    public PpatkResultEntity() {
    	
    }

	public PpatkResultEntity(Long version, String message,
			String registrationId, String status, String identificationNumber) {
		this.version = version;
		this.message = message;
		this.registrationId = registrationId;
		this.status = status;
		this.identificationNumber = identificationNumber;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getOnlineRegistrationId() {
		return onlineRegistrationId;
	}

	public void setOnlineRegistrationId(String onlineRegistrationId) {
		this.onlineRegistrationId = onlineRegistrationId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public static String getGetidentificationnumber() {
		return getIdentificationNumber;
	}

	@Override
	public String toString() {
		return "PpatkResultEntity [id=" + id + ", version=" + version + ", message=" + message + ", dateCreated="
				+ dateCreated + ", lastUpdated=" + lastUpdated + ", onlineRegistrationId=" + onlineRegistrationId
				+ ", registrationId=" + registrationId + ", createdById=" + createdById + ", status=" + status
				+ ", identificationNumber=" + identificationNumber + "]";
	}    
}
