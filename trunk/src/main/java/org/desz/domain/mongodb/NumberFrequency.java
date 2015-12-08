package org.desz.domain.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "number_frequencies")
public class NumberFrequency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Indexed
	private String number;
	private int count;

	public NumberFrequency() {
		super();
		// TODO Auto-generated constructor stub
	}

	// @PersistenceConstructor
	public NumberFrequency(String number, int count) {
		// super();
		this.number = number;
		this.count = count;
	}

	// @PersistenceConstructor
	public NumberFrequency(String number) {
		this.number = number;
		this.count = 1;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("NumberFrequency [id=%s, number=%s, count=%s]", id, number, count);
	}

}
