package com.p6FullStack.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.dto.ThemesDto;
import com.p6FullStack.mappers.ThemesMapper;
import com.p6FullStack.model.Themes;
import com.p6FullStack.service.ThemesService;
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
    
	/**
	 * Get All Themes subscribed by UserId
	 * @param UserId
	 * @return List of Themes
	 */
	@Operation(summary = "Get all themes subscribed by UserId", description = "Retourne touts les thèmes souscrits par l'utilisateur")
    @GetMapping("/subscribed/{userId}")
    public ResponseEntity<List<ThemesDto>> findAllSubscribedThemes(@PathVariable("userId") String userId) {
        try {
            List<Themes> themes = this.themesService.findSubscriptionByUserId(Long.parseLong(userId));
            return ResponseEntity.ok().body(themesMapper.mapListToDto(themes));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

	/**
	 * Get All Themes not subscribed yet by UserId
	 * @param UserId
	 * @return List of Themes
	 */
	@Operation(summary = "Get all themes not subscribed yet by UserId", description = "Retourne touts les thèmes non souscrits par l'utilisateur")
    @GetMapping("/notsubscribed/{userId}")
    public ResponseEntity<List<ThemesDto>> findAllNotSubscribedThemes(@PathVariable("userId") String userId) {
        try {
            List<Themes> themes = this.themesService.findUnSubscriptionByUserId(Long.parseLong(userId));
            return ResponseEntity.ok().body(themesMapper.mapListToDto(themes));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public record ThemesResponse(List<ThemesDto> themes) {}

}