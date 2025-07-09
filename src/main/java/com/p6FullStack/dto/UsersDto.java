package com.p6FullStack.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.p6FullStack.model.Themes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersDto {

	private Long id;

	private String email;
	
	private String name;
	
	@JsonIgnore
	private String password;

	private List<Themes> listThemes;

}
