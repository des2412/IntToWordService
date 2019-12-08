package org.desz.numbertoword.results;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class Word {
	private final String quint;
	private final String quadr;
	private final String trill;
	private final String bill;
	private final String mill;
	private final String thou;
	private final String hund;

}
