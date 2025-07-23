package com.p6FullStack.dto;

import java.util.List;
import com.p6FullStack.model.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThemesDto {

	private Long id;

	private String title;

	private String content;
	
	private List<Users> listUsers;

}
