package org.desz.numbertoword.factory;

import org.desz.numbertoword.INumberToWordMapper;

public interface INumberToWordFactory {
	public INumberToWordMapper getNumberToWordMapper() throws Exception;
}
