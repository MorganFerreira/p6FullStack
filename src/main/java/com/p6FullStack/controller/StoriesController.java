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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.dto.StoriesDto;
import com.p6FullStack.mappers.StoriesMapper;
import com.p6FullStack.model.Stories;
import com.p6FullStack.service.StoriesService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/stories")
public class StoriesController {
	
	@Autowired
	private StoriesService storiesService;
	
	private final StoriesMapper storiesMapper;
	
	public StoriesController(StoriesService storiesService,
							 StoriesMapper storiesMapper) {
		this.storiesService = storiesService;
		this.storiesMapper = storiesMapper;
	}

	/**
	 * Get All Stories
	 * @param 
	 * @return List of Stories
	 */
	@Operation(summary = "Get all stories", description = "Retourne toutes les articles")
    @GetMapping("")
    public ResponseEntity<?> findAllStories() {
        List<Stories> stories = storiesService.getAllStories();        
        if(stories != null){
            List<StoriesDto> storiesDto = stories.stream()
            									 .map(story -> storiesMapper.mapToDto(story))
            									 .collect(Collectors.toList());
            return ResponseEntity.ok().body(storiesDto);
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    /**
	 * Get Story by Id
	 * @param Id
	 * @return Story
	 */
	@Operation(summary = "Get story by id", description = "Retourne un article par son id")
    @GetMapping("/{id}")
    public ResponseEntity<StoriesDto> getStoryById(@PathVariable String id) {
        
		Optional<Stories> story = storiesService.getStoryById(id);
        if(story != null){
        	StoriesDto storyDto = storiesMapper.mapToDtoWithOptional(story);
            return ResponseEntity.ok(storyDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
	/**
	 * Create Story
	 * @param FormData
	 * @return StoryResponse
	 * @throws Exception 
	 */
	@Operation(summary = "Create story", description = "Créés un article")
    @PostMapping("")
    public ResponseEntity<StoryResponse> createStory(@RequestHeader("Authorization") String token, @RequestBody StoriesDto storyDto) throws Exception {
	
        storiesService.createStory(storiesMapper.mapToEntity(storyDto));
        return ResponseEntity.ok(new StoryResponse("Article créé !!!"));
   }

    public record StoryResponse(String response) {}

}
