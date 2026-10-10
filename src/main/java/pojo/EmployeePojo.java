package pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(setterPrefix = "set")
@ToString
@AllArgsConstructor
public class EmployeePojo {
	private int empid;
	private String name;
	private String job;

}
