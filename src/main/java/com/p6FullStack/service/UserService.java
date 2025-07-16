package com.p6FullStack.service;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.p6FullStack.configuration.SpringSecurityConfig;
import com.p6FullStack.dto.UsersDto;
import com.p6FullStack.model.Themes;
import com.p6FullStack.model.Users;
import com.p6FullStack.repository.ThemesRepository;
import com.p6FullStack.repository.UsersRepository;
import lombok.Data;

@Data
@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ThemesRepository themesRepository;

	@Autowired
	private SpringSecurityConfig springSecurityConfig;
	
    public Users saveUsers(String email, String name, String password) {
		Users newUsers = new Users(email, name, password);
		String visiblePassword = newUsers.getPassword();
		newUsers.setPassword(springSecurityConfig.encoder().encode(visiblePassword));
		return usersRepository.save(newUsers);
    }
    
    public Users getUserByName(String name){
        return usersRepository.findByName(name);
    }
    
    public Optional<Users> getUserById(String id){
        Long ident = Long.parseLong(id);
        return usersRepository.findById(ident);
    }
    
    public String subscribe(Long userId, Long themeId) {
        Themes theme = this.themesRepository.findById(themeId).orElse(null);
        Users user = this.usersRepository.findById(userId).orElse(null);
        
        if (theme == null || user == null) {
            return "Theme or User not found";
        }

        boolean alreadySubscribe = user.getListThemes().stream().anyMatch(t -> t.getId().equals(themeId));
        if(alreadySubscribe) {
            return "User already subscribed to this theme";
        }

        user.getListThemes().add(theme);
        this.usersRepository.save(user);
        return "User subscribed to theme successfully";
        
    }

    public String unSubscribe(Long userId, Long themeId) {
        Themes theme = this.themesRepository.findById(themeId).orElse(null);
        Users user = this.usersRepository.findById(userId).orElse(null);
        
        if (theme == null || user == null) {
            return "Theme or User not found";
        }

        user.setListThemes(user.getListThemes().stream().filter(t -> !t.getId().equals(themeId)).collect(Collectors.toList()));
        this.usersRepository.save(user);
        return "User unsubscribed from theme successfully";
    }
    
	public String updateUser(UsersDto userDtoToUpdate) {
        Users userToUpdate = usersRepository.findById(userDtoToUpdate.getId()).orElse(null);
        if(userToUpdate != null){
        	userToUpdate.setEmail(userDtoToUpdate.getEmail());
        	userToUpdate.setName(userDtoToUpdate.getName());
        	userToUpdate.setPassword(springSecurityConfig.encoder().encode(userDtoToUpdate.getPassword()));
            usersRepository.save(userToUpdate);
            return "User updated";
        } else {
            return "User not found";
        }
	}
}
