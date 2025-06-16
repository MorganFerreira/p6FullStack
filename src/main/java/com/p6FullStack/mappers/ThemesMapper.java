package com.p6FullStack.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.p6FullStack.dto.ThemesDto;
import com.p6FullStack.model.Themes;

@Component
public class ThemesMapper {

	@Bean
    public ModelMapper themesModelMapper() {
        return new ModelMapper();
    }
	
	public ThemesDto mapToDto(Themes themes){
        return themesModelMapper().map(themes, ThemesDto.class);
    }

    public Themes mapToEntity(ThemesDto themesDto){
        return themesModelMapper().map(themesDto, Themes.class);
    }

	public ThemesDto mapToDtoWithOptional(Object themes){
        return themesModelMapper().map(themes, ThemesDto.class);
    }

    public Themes mapToEntityWithOptional(Object themesDto){
        return themesModelMapper().map(themesDto, Themes.class);
    }

}
