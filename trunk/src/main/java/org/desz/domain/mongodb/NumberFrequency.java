package org.desz.domain.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

//@TypeAlias("nf")
@Document
public class NumberFrequency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private final String number;
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
		this.count = 1;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberFrequency other = (NumberFrequency) obj;
		if (count != other.count)
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NumberFrequency [number=" + number + ", count=" + count + "]";
	}

}
