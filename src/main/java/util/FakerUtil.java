package util;

import com.github.javafaker.Faker;

public class FakerUtil {
	
	private FakerUtil()
	{
		
	}
	
	private static String name;
	private static int empid;
	private static String job;
	public static String getName() {
		name=Faker.instance().name().fullName();
		return name;
	}
	public static int getEmpid() {
		empid=Faker.instance().number().randomDigitNotZero();
		return empid;
	}
	public static String getJob() {
		job=Faker.instance().job().title();
		return job;
	}
	
	

}
