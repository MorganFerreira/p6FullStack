package com.p6FullStack.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.controller.CommentsController.CommentResponse;
import com.p6FullStack.controller.CommentsController.CommentsResponse;
import com.p6FullStack.dto.ThemesDto;
import com.p6FullStack.mappers.ThemesMapper;
import com.p6FullStack.model.Comments;
import com.p6FullStack.model.Themes;
import com.p6FullStack.model.Users;
import com.p6FullStack.service.CommentsService;
import com.p6FullStack.service.JWTService;
import com.p6FullStack.service.ThemesService;
import com.p6FullStack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/themes")
public class ThemesController {
	
	@Autowired
	private ThemesService themesService;
	
	private final ThemesMapper themesMapper;
	
	public ThemesController(ThemesService themesService, ThemesMapper themesMapper) {
		this.themesService = themesService;
		this.themesMapper = themesMapper;
	}

	/**
	 * Get All Themes
	 * @param 
	 * @return List of Themes
	 */
	@Operation(summary = "Get all themes", description = "Retourne touts les thèmes")
    @GetMapping("")
    public ResponseEntity<ThemesResponse> findAllThemes() {
        List<Themes> themes = themesService.getAllThemes();        
        if(themes != null){
            List<ThemesDto> themesDto = themes.stream()
            								  .map(theme -> themesMapper.mapToDto(theme))
            								  .collect(Collectors.toList());
            return ResponseEntity.ok(new ThemesResponse(themesDto));
        } else {
            return ResponseEntity.status(404).build();
        }
    }
    
    public record ThemesResponse(List<ThemesDto> themes) {}

}