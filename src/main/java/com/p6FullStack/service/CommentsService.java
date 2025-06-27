package com.p6FullStack.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.p6FullStack.model.Comments;
import com.p6FullStack.repository.CommentsRepository;
import lombok.Data;

@Data
@Service
public class CommentsService {

	private final CommentsRepository commentsRepository;
	
	@Autowired
	private final StoriesService storiesService;
	
	public List<Comments> getAllComments() {
		
		return commentsRepository.findAll();
	}
	
	public Comments createComment(Comments newComment, String userName, String storyId) {
		
		newComment.setCreatedAt(LocalDateTime.now());
		return commentsRepository.save(newComment);
	}
}
