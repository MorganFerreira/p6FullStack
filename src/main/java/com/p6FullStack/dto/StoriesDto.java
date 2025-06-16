package com.p6FullStack.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import com.p6FullStack.model.Comments;
import com.p6FullStack.model.Themes;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoriesDto {

	private int storyId;
	
	private String title;
	
	private String content;
	
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private int authorId;
	
	private Themes themeId;
	
	private List<Comments> comments;

}
