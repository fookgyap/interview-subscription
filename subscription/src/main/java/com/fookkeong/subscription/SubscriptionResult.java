package com.fookkeong.subscription;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SubscriptionResult {

	private Double invAmountOutput;
	private String subscriptionTypeOutput;
	private List<String> invoiceDateList;
	
	public SubscriptionResult() {
		
	}

	public Double getInvAmountOutput() {
		return invAmountOutput;
	}

	public void setInvAmountOutput(Double invAmountOutput) {
		this.invAmountOutput = invAmountOutput;
	}

	public String getSubscriptionTypeOutput() {
		return subscriptionTypeOutput;
	}

	public void setSubscriptionTypeOutput(String subscriptionTypeOutput) {
		this.subscriptionTypeOutput = subscriptionTypeOutput;
	}

	public List<String> getInvoiceDateList() {
		return invoiceDateList;
	}

	public void setInvoiceDateList(List<String> invoiceDateList) {
		this.invoiceDateList = invoiceDateList;
	}
	
}
