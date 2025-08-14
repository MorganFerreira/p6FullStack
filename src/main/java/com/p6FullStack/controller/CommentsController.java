package com.p6FullStack.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.dto.CommentsDto;
import com.p6FullStack.mappers.CommentsMapper;
import com.p6FullStack.model.Comments;
import com.p6FullStack.service.CommentsService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	private final CommentsMapper commentsMapper;
	
	public CommentsController(CommentsService commentsService,
							  CommentsMapper commentsMapper) {
		this.commentsService = commentsService;
		this.commentsMapper = commentsMapper;
	}

	/**
	 * Get All Comments
	 * @param 
	 * @return List of Comments
	 */
	@Operation(summary = "Get all comments", description = "Retourne toutes les commentaires")
    @GetMapping("")
    public ResponseEntity<?> findAllComments() {
        List<Comments> comments = commentsService.getAllComments();        
        if(comments != null){
            List<CommentsDto> commentsDto = comments.stream()
            									 	.map(comment -> commentsMapper.mapToDto(comment))
            									 	.collect(Collectors.toList());
            return ResponseEntity.ok().body(commentsDto);
        } else {
            return ResponseEntity.status(404).build();
        }
    }
    
	/**
	 * Create Comment
	 * @param FormData
	 * @return CommentResponse
	 * @throws Exception 
	 */
	@Operation(summary = "Create comment", description = "Créés un commentaire")
    @PostMapping("")
    public ResponseEntity<CommentResponse> createComment(@RequestHeader("Authorization") String token, @RequestBody CommentsDto commentDto) throws Exception {
    
        commentsService.createComment(commentsMapper.mapToEntity(commentDto));
        return ResponseEntity.ok(new CommentResponse("Commentaire créé !!!"));
    }
    
    public record CommentResponse(String response) {}

}
