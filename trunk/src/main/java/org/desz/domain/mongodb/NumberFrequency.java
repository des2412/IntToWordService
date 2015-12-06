package org.desz.domain.mongodb;

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
	private final String number;
	private Integer count;

	@PersistenceConstructor
	public NumberFrequency(String number, int count) {
		this.number = number;
		this.count = new Integer(count);
	}

	@PersistenceConstructor
	public NumberFrequency(String number) {
		this.number = number;
		this.count = new Integer(1);
	}

	public String getNumber() {
		return number;
	}

	public int getCount() {
		return count;
	}

	public void incrementCount() {
		this.count = Integer.valueOf(count + 1);
	}

	@Override
	public String toString() {
		return "NumberFrequency [number=" + number + ", count=" + count + "]";
	}

}
