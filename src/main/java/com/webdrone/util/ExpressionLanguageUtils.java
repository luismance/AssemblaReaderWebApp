package com.webdrone.util;

import java.util.Map;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class ExpressionLanguageUtils {

	public static ExpressionLanguageResultEnum evaluate(Map<String, String> fieldMap, String expressionLanguage) {

		Boolean o = false;

		try {
			JexlEngine jexl = new JexlBuilder().create();

			JexlExpression e = jexl.createExpression(expressionLanguage);

			JexlContext jc = new MapContext();

			for (String field : fieldMap.keySet()) {
				jc.set(field, fieldMap.get(field));
			}

			o = (Boolean) e.evaluate(jc);
			if (o) {
				return ExpressionLanguageResultEnum.COMPLETE_TRUE;
			} else {
				return ExpressionLanguageResultEnum.COMPLETE_FALSE;
			}
		} catch (JexlException e) {
			e.printStackTrace();
			return ExpressionLanguageResultEnum.INCOMPLETE;
		}
	}
}
