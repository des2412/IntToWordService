package org.desz.numbertoword;

public interface INumberToWordMapper {

	String getWord(Integer num) throws Exception;

	void initialiseMapping();

}
