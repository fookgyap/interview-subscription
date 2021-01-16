package com.fookkeong.subscription;

public enum SubscriptionType {
	
	DAILY("d","DAILY"),
	WEEKLY("w", "WEEKLY"),
	MONTHLY("m","MONTHLY");
	
	private String subCode;
	private String name;

	
	private SubscriptionType(String subCode, String name) {
		this.subCode = subCode;
		this.name = name;
	}


	public String getSubCode() {
		return subCode;
	}


	public String getName() {
		return name;
	}
	
}
