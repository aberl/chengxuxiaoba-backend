package com.chengxuxiaoba.video.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalUtil {
	public static String convert(BigDecimal value, String format) {
		try {

			DecimalFormat df4 = new DecimalFormat(format);
			String formatStr = df4.format(value);
			return formatStr;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static BigDecimal convert(String valueStr)
	{
		try{
			if(valueStr == null)
				return null;
			
			return new BigDecimal(valueStr.replace(",", ""));
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public  static  BigDecimal sum(List<BigDecimal> valueList) {
		if (valueList == null || valueList.size() == 0)
			return null;

		BigDecimal sumValue=new BigDecimal(0);

		for(BigDecimal value :  valueList)
			sumValue=sumValue.add(value);

		return sumValue;
	}
}
