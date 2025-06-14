package com.p6FullStack.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.p6FullStack.configuration.SpringSecurityConfig;
import com.p6FullStack.model.Users;
import com.p6FullStack.repository.UsersRepository;
import lombok.Data;

@Data
@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private SpringSecurityConfig springSecurityConfig;
	
    public Users saveUsers(String email, String name, String password) {
		Users newUsers = new Users(email, name, password);
		String visiblePassword = newUsers.getPassword();
		newUsers.setPassword(springSecurityConfig.passwordEncoder().encode(visiblePassword));
		return usersRepository.save(newUsers);
    }
    
    public Users getUserByName(String name){
        return usersRepository.findByName(name);
    }
    
    public Optional<Users> getUserById(String id){
        Integer ident = Integer.parseInt(id);
        return usersRepository.findById(ident);
    }
}
