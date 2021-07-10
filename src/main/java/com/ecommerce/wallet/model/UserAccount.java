package com.ecommerce.wallet.model;

import java.util.Date;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "useraccount")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "dteCreated", "dateModified" }, allowGetters = true)
public class UserAccount {

	@Column(name = "email")
	private String email;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dteCreated;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dteModified;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_id", referencedColumnName = "id")
	private Wallet wallet;
	
	public UserAccount() {
	}

	public UserAccount(String email) {
		this.email = email;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDteCreated() {
		return dteCreated;
	}

	public void setDteCreated(Date dteCreated) {
		this.dteCreated = dteCreated;
	}

	public Date getDateModified() {
		return dteModified;
	}

	public void setDateModified(Date dteModified) {
		this.dteModified = dteModified;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}
