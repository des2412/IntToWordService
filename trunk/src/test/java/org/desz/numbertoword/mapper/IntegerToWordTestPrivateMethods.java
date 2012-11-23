/**
 * 
 */
package org.desz.numbertoword.mapper;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.desz.language.LanguageSupport;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author des
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ IntegerToWordMapper.class, LanguageSupport.class })
public class IntegerToWordTestPrivateMethods extends IntegerToWordMapperTest {

	@Test
	public void test() {

		IntegerToWordMapper tested = PowerMock.createPartialMock(
				IntegerToWordMapper.class, "getWordForInt");

		// LanguageSupport languageSupport =
		// PowerMock.createMock(LanguageSupport.class);

		Map<String, String> numToWordMap = new HashMap<String, String>();

		numToWordMap.put("0", "Zero");
		numToWordMap.put("1", "One");
		numToWordMap.put("20", "Twenty");

		tested.setMapping(numToWordMap);

		Object[] args = new Object[1];
		args[0] = new BigInteger("121");

		try {
			// expect(languageSupport.getHunUnit()).andReturn("hundred");
			// PowerMock.expectLastCall().once();
			PowerMock.expectPrivate(tested, "getWordForInt", args).andReturn(
					"One hundred and twenty one");
		} catch (Exception e) {
			LOGGER.severe("expectPrivate exception: " + e.getMessage());
		}

		replay(tested);

		try {
			assertEquals(tested.getWord(new BigInteger("121")),
					"One hundred and twenty one");
		} catch (IntegerToWordException e) {
			LOGGER.severe(e.getMessage());
		}

		verify(tested);

		// fail("Not yet implemented");
	}

}
