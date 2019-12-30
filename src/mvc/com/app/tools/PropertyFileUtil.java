package com.app.tools;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertyFileUtil {
		private static  Properties properties;

		public static void init(){
			try {
				properties = PropertiesLoaderUtils.loadAllProperties("properties/app.properties");
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}

	public  static String getValue(String key) {
		String value = null;
		try {
			value = properties.getProperty(key);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return value;
	}
}