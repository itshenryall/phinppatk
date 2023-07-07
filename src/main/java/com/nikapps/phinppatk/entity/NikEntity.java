package com.nikapps.phinppatk.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="registration_personal_information")
public class NikEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    
    @Column(name = "identification_number") 
    private String identificationNumber;
    
    @Column(name="date_created")
	private Date dateCreated;
    
    @Column(name="version")
	private Long version;
    
    @Column(name="registration_status")
	private String registrationStatus;
    
    @Column(name="registration_id")
	private String registrationId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	@Override
	public String toString() {
		return "NikEntity [id=" + id + ", identificationNumber=" + identificationNumber + ", dateCreated=" + dateCreated
				+ ", version=" + version + ", registrationStatus=" + registrationStatus + ", registrationId="
				+ registrationId + "]";
	}
	
}
