package com.p6FullStack.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.dto.CommentsDto;
import com.p6FullStack.mappers.CommentsMapper;
import com.p6FullStack.model.Comments;
import com.p6FullStack.model.Users;
import com.p6FullStack.service.CommentsService;
import com.p6FullStack.service.JWTService;
import com.p6FullStack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	@Autowired
	private final UserService userService;
	
	private final CommentsMapper commentsMapper;
	
	private final JWTService jwtService;
	
	public CommentsController(CommentsService commentsService,
							  CommentsMapper commentsMapper,
							  UserService userService,
							  JWTService jwtService) {
		this.commentsService = commentsService;
		this.commentsMapper = commentsMapper;
		this.userService = userService;
		this.jwtService = jwtService;
	}

	/**
	 * Get All Comments
	 * @param 
	 * @return List of Comments
	 */
	@Operation(summary = "Get all comments", description = "Retourne toutes les commentaires")
    @GetMapping("")
    public ResponseEntity<CommentsResponse> findAllComments() {
        List<Comments> comments = commentsService.getAllComments();        
        if(comments != null){
            List<CommentsDto> commentsDto = comments.stream()
            									 	.map(comment -> commentsMapper.mapToDto(comment))
            									 	.collect(Collectors.toList());
            return ResponseEntity.ok(new CommentsResponse(commentsDto));
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
    public ResponseEntity<CommentResponse> createComment(@RequestHeader("Authorization") String token, @ModelAttribute CommentsDto commentDto) throws Exception {
    
		String userName = jwtService.getNameFromToken(token);
        commentsService.createComment(commentsMapper.mapToEntity(commentDto), userName);
        return ResponseEntity.ok(new CommentResponse("Commentaire créé !!!"));
    }
    
    public record CommentResponse(String response) {}
    public record CommentsResponse(List<CommentsDto> comments) {}

}
