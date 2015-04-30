package com.cds.web.test.domain;

public class DomainObject {

	private String name;
	private String address;
	
	public DomainObject()
	{
		name = "default";
		address = "nowhere";
	}
	public DomainObject(String name, String address)
	{
		this.name = name;
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
