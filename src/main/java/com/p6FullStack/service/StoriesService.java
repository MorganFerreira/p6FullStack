package com.p6FullStack.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.p6FullStack.model.Stories;
import com.p6FullStack.repository.StoriesRepository;
import lombok.Data;

@Data
@Service
public class StoriesService {

	private final StoriesRepository storiesRepository;

	public List<Stories> getAllStories() {
		
		return storiesRepository.findAll();
	}

	public Optional<Stories> getStoryById(String id) {
		
		Long ident = Long.parseLong(id);
		return storiesRepository.findById(ident);
	}

	public Stories createStory(Stories newStory) {
		
		newStory.setCreatedAt(LocalDateTime.now());
		return storiesRepository.save(newStory);
	}

}
