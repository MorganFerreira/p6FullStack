package com.p6FullStack.dto;

import java.util.List;
import com.p6FullStack.model.Stories;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ThemesDto {

	private int themeId;

	private String title;

	private String content;

	private List<Stories> stories;

}
