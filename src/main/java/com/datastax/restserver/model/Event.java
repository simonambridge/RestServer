package com.datastax.restserver.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Event {

	private String name;
	private Date time;
	private BigDecimal value;

	public Event() {
		super();
	}

	public String getName() { return name; }
	public void setName(String name) {
		this.name = name;
	}


	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}


	public BigDecimal getValue() { return value; }
	public void setValue(BigDecimal value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return "Event [Name=" + name + ", Time=" + time + ", Value =" + value  + "]";
	}

}
