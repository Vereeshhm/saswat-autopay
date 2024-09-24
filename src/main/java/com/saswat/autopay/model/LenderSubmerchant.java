package com.saswat.autopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LenderSubmerchant")
public class LenderSubmerchant {

    @Id
    private Long id;

    @Column(name = "lender_name")
    private String lenderName;

    @Column(name = "sub_merchant_id", unique = true, nullable = false)
    private String subMerchantId;

    @Column(name="environment")
    private String environment;
    
    public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getSubMerchantId() {
        return subMerchantId;
    }

    public void setSubMerchantId(String subMerchantId) {
        this.subMerchantId = subMerchantId;
    }
}
