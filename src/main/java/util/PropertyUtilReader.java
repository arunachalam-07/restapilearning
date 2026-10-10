package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties;

import constants.FrameworkConstantSingleton;

public class PropertyUtilReader {
	
	private PropertyUtilReader() {}
	
	private static HashMap<String, String> map=new HashMap<String, String>();
	private static Properties properties=new Properties();
	
	static
	{
		try(FileInputStream file=new FileInputStream(FrameworkConstantSingleton.getInstance().getPropertyFilepath()))
		{
			properties.load(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Map.Entry<Object, Object> temp:properties.entrySet())
		{
			map.put(temp.getKey().toString(), temp.getValue().toString());
		}
	}
	
	
	public static String getValue(Enum key)
	{
		return map.get(key.toString().toLowerCase());
	}

}
