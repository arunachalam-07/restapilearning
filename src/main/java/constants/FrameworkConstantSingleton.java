package constants;

import lombok.Getter;

public class FrameworkConstantSingleton {
	
	private FrameworkConstantSingleton()
	{
		
	}
	
	private static @Getter String propertyFilepath=System.getProperty("user.dir")+"/src/test/resources/config.properties";
	private static @Getter String reportpath="index.html";
	private static @Getter String jsonSchemaPath=System.getProperty("user.dir")+"/src/test/resources/Employeeschema.json";
	
	private static FrameworkConstantSingleton Instance=null;
	
	public static FrameworkConstantSingleton getInstance()
	{
		if(Instance==null)
		{
			Instance=new FrameworkConstantSingleton();
		}
		
		return Instance;
	}
	
	

}
