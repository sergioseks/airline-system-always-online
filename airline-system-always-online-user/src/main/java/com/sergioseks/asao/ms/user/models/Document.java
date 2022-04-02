package com.sergioseks.asao.ms.user.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "documents" )
@NamedQueries({
	@NamedQuery(name="Document.documentByUser",
			query="select d from Document d where d.user=?1")
})
public class Document implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column( name = "document_type" )
    private String documentType;
    
	@Column( name = "number" )
    private String number;
    
	@Column( name = "expiry_date" )
    private String expiryDate;
    
	@Column( name = "issuance_country" )
    private String issuanceCountry;
    
	@Column( name = "nationality" )
    private String nationality;
    
	@Column( name = "holder" )
    private boolean holder;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getIssuanceCountry() {
		return issuanceCountry;
	}

	public void setIssuanceCountry(String issuanceCountry) {
		this.issuanceCountry = issuanceCountry;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public boolean isHolder() {
		return holder;
	}

	public void setHolder(boolean holder) {
		this.holder = holder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
