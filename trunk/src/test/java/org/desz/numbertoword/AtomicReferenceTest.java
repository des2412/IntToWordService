package org.desz.numbertoword;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LANGUAGE;
import org.junit.Before;
import org.junit.Test;

public class AtomicReferenceTest {
	final AtomicReference<IntegerToWordMapper> ref = new AtomicReference<IntegerToWordMapper>();

	IntegerToWordMapper mapper = null;
	Object[] args = new Object[1];

	@Before
	public void init() throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Constructor<?>[] c = IntegerToWordMapper.class.getDeclaredConstructors();
		c[0].setAccessible(true);
		args[0] = new LanguageSupport(PROVISIONED_LANGUAGE.UK);
		mapper = (IntegerToWordMapper) c[0].newInstance(args);
	}

	@Test
	public void test() throws Exception {

		assertTrue(ref.compareAndSet(null, mapper));
		if (ref.getAndSet(mapper) == null) {
			throw new Exception();
		}
		ref.set(mapper);

		// ref.set(null);
	}

}
