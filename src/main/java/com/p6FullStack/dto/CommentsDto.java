package com.p6FullStack.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import com.p6FullStack.model.Stories;
import com.p6FullStack.model.Users;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentsDto {

	private int id;

	private String content;

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private Users associatedUser;

	private Stories associatedStory;

}
