/**
 * 
 */
package org.desz.numbertoword.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.desz.language.LanguageSupport;
import org.desz.numbertoword.enums.EnumHolder.PROVISIONED_LN;
import org.desz.numbertoword.exceptions.FactoryMapperRemovalException;
import org.desz.numbertoword.exceptions.IntegerToWordException;
import org.desz.numbertoword.exceptions.NumberToWordFactoryException;
import org.desz.numbertoword.factory.IntegerToWordEnumFactory;
import org.desz.numbertoword.mapper.IFNumberToWordMapper;
import org.desz.numbertoword.mapper.IntegerToWordMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.easymock.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import static org.easymock.EasyMock.expect;

/**
 * @author des
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IntegerToWordMapper.class, LanguageSupport.class})
public class IntegerToWordTestPrivateMethods extends IntegerToWordMapperTest {

	@Test
	public void test() {

		IntegerToWordMapper tested = PowerMock.createPartialMock(
				IntegerToWordMapper.class, "getWordForInt");
		
		//LanguageSupport languageSupport = PowerMock.createMock(LanguageSupport.class);
		
		Map<String, String> numToWordMap = new HashMap<String, String>();
		
		
		numToWordMap.put("0", "Zero");
		numToWordMap.put("1", "One");
		numToWordMap.put("20", "Twenty");
		
		tested.setMapping(numToWordMap);
		
		Object[] args = new Object[1];
		args[0] = new BigInteger("121");
		

		try {
			//expect(languageSupport.getHunUnit()).andReturn("hundred");
			//PowerMock.expectLastCall().once();
			PowerMock.expectPrivate(tested, "getWordForInt", args).andReturn("One hundred and twenty one");
		} catch (Exception e) {
			LOGGER.severe("expectPrivate exception: " + e.getMessage());
		}

		replay(tested);

		try {
			assertEquals(tested.getWord(new BigInteger("121")), "One hundred and twenty one");
		} catch (IntegerToWordException e) {
			LOGGER.severe(e.getMessage());
		}

		verify(tested);

		//fail("Not yet implemented");
	}

}
