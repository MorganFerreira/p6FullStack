package com.p6FullStack.mappers;

import java.util.List;
import java.util.stream.Collectors;

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

	public List<ThemesDto> mapListToDto(List<Themes> themes){
		return themes.stream()
					 .map(theme -> themesModelMapper().map(theme, ThemesDto.class))
					 .collect(Collectors.toList());
    }

	public ThemesDto mapToDtoWithOptional(Object themes){
        return themesModelMapper().map(themes, ThemesDto.class);
    }

    public Themes mapToEntityWithOptional(Object themesDto){
        return themesModelMapper().map(themesDto, Themes.class);
    }

}
