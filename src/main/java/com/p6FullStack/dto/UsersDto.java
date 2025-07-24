package com.p6FullStack.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto {

	private Long id;

	private String email;
	
	private String name;
	
	private String password;

}
