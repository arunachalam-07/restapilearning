package pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EmployeePojo {
	private Integer empid;
	private String name;
	private String job;

}
