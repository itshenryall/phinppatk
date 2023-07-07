package com.nikapps.phinppatk.entity;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="registration")
public class RegistEntity {
	
	public static final String getIdentificationNumber = null;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
	
	@Column(name="version")
	private Long version;
	
	@Column(name = "online_registration_id") 
	private String onlineRegistrationId;
	
	@CreationTimestamp
	@Column(name="last_updated") 
	private Date lastUpdated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getOnlineRegistrationId() {
		return onlineRegistrationId;
	}

	public void setOnlineRegistrationId(String onlineRegistrationId) {
		this.onlineRegistrationId = onlineRegistrationId;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public static String getGetidentificationnumber() {
		return getIdentificationNumber;
	}

	@Override
	public String toString() {
		return "RegistEntity [id=" + id + ", version=" + version + ", onlineRegistrationId=" + onlineRegistrationId
				+ ", lastUpdated=" + lastUpdated + "]";
	}
		  
	
}
	
