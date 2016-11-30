package com.yonder.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EntryTools {
	
	@SuppressWarnings("unchecked")
	public static <T> T cloneEntry(T entry) {
		T result = null;
		try {
			result = (T)entry.getClass().newInstance();
			Field[] fields = entry.getClass().getDeclaredFields();
			for (Field field : fields) {
				try {
					if ((field.getModifiers() & Modifier.STATIC) <= 0 && (field.getModifiers() & Modifier.FINAL) <= 0) {//不是static，不是final
						field.set(result, field.get(entry));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
