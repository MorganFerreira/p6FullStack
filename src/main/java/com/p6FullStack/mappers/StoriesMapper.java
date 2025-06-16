package com.p6FullStack.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.p6FullStack.dto.StoriesDto;
import com.p6FullStack.model.Stories;

@Component
public class StoriesMapper {

	@Bean
    public ModelMapper storiesModelMapper() {
        return new ModelMapper();
    }
	
	public StoriesDto mapToDto(Stories stories){
        return storiesModelMapper().map(stories, StoriesDto.class);
    }

    public Stories mapToEntity(StoriesDto storiesDto){
        return storiesModelMapper().map(storiesDto, Stories.class);
    }

	public StoriesDto mapToDtoWithOptional(Object stories){
        return storiesModelMapper().map(stories, StoriesDto.class);
    }

    public Stories mapToEntityWithOptional(Object storiesDto){
        return storiesModelMapper().map(storiesDto, Stories.class);
    }

}
