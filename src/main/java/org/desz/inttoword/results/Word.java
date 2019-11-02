package org.desz.inttoword.results;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Word {

	private final String bill;
	private final String mill;
	private final String thou;
	private final String hund;

}
