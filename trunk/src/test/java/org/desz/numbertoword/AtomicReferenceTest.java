package org.desz.numbertoword;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReference;

import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.junit.Test;

public class AtomicReferenceTest {
	final AtomicReference<NumberToWordMapper> ref = new AtomicReference<NumberToWordMapper>();

	final NumberToWordMapper mapper = new NumberToWordMapper(PROVISIONED_LANGUAGE.UK);
	@Test
	public void test() throws Exception {
		
		assertTrue(ref.compareAndSet(null, new NumberToWordMapper(PROVISIONED_LANGUAGE.UK)));
		if(ref.getAndSet(mapper) == null){
			throw new Exception();
		}
		ref.set(mapper);
		
		//ref.set(null);
	}

}
