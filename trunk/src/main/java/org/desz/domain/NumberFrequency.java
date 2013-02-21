package org.desz.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NumberFrequency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String number;
	private int count;

	@PersistenceConstructor
	public NumberFrequency(String number, int count) {
		super();
		this.number = number;
		this.count = count;
	}

	@PersistenceConstructor
	public NumberFrequency(String number) {
		this.number = number;
	}

	public NumberFrequency() {
	}

	public String getNumber() {
		return number;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "NumberFrequency [number=" + number + ", count=" + count + "]";
	}

}
