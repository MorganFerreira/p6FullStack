package com.p6FullStack.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto {

	private int userId;

	private String email;
	
	private String name;
	
	@JsonIgnore
	private String password;

}
