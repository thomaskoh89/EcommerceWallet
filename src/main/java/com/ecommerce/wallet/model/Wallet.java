package com.ecommerce.wallet.model;

import java.util.Date;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Wallet")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "dteCreated", "dteModified" }, allowGetters = true)
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "balanceAmt", columnDefinition="Decimal(10,2)")
	private double balanceAmt;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dteCreated;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dteModified;
	
	@OneToOne(mappedBy = "wallet")
	private UserAccount useraccount;
	
	public UserAccount getUseraccount() {
		return useraccount;
	}

	public void setUseraccount(UserAccount useraccount) {
		this.useraccount = useraccount;
	}

	public Wallet() {}
	
	public Wallet(double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public int getId() {
		return id;
	}

	public void setWalletId(int id) {
		this.id = id;
	}

	public double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public Date getDteCreated() {
		return dteCreated;
	}

	public void setDteCreated(Date dteCreated) {
		this.dteCreated = dteCreated;
	}

	public Date getDteModified() {
		return dteModified;
	}

	public void setDteModified(Date dteModified) {
		this.dteModified = dteModified;
	}
}
