package com.p6FullStack.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.p6FullStack.dto.UsersDto;
import com.p6FullStack.model.Users;

@Component
public class UsersMapper {

	@Bean
    public ModelMapper usersModelMapper() {
        return new ModelMapper();
    }
	
	public UsersDto mapToDto(Users users){
        return usersModelMapper().map(users, UsersDto.class);
    }

    public Users mapToEntity(UsersDto usersDto){
        return usersModelMapper().map(usersDto, Users.class);
    }

	public UsersDto mapToDtoWithOptional(Object users){
        return usersModelMapper().map(users, UsersDto.class);
    }

    public Users mapToEntityWithOptional(Object usersDto){
        return usersModelMapper().map(usersDto, Users.class);
    }

}
