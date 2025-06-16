package com.p6FullStack.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.p6FullStack.dto.CommentsDto;
import com.p6FullStack.model.Comments;

@Component
public class CommentsMapper {
	
	@Bean
    public ModelMapper commentsModelMapper() {
        return new ModelMapper();
    }
	
	public CommentsDto mapToDto(Comments comments){
        return commentsModelMapper().map(comments, CommentsDto.class);
    }

    public Comments mapToEntity(CommentsDto commentsDto){
        return commentsModelMapper().map(commentsDto, Comments.class);
    }

	public CommentsDto mapToDtoWithOptional(Object comments){
        return commentsModelMapper().map(comments, CommentsDto.class);
    }

    public Comments mapToEntityWithOptional(Object commentsDto){
        return commentsModelMapper().map(commentsDto, Comments.class);
    }

}