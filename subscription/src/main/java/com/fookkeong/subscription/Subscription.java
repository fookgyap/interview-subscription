package com.fookkeong.subscription;

public class Subscription {
	
	private Double amount;
	private String subscriptionType;
	private String dayOfWeek;
	private String startDate;
	private String endDate;
	
	public Subscription() {}
	
	public Subscription(Double amount, String subscriptionType, String dayOfWeek, String startDate, String endDate) {
		this.amount = amount;
		this.subscriptionType = subscriptionType;
		this.dayOfWeek = dayOfWeek;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

}
