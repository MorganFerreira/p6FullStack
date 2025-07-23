package com.p6FullStack.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.p6FullStack.model.Themes;
import com.p6FullStack.model.Users;
import com.p6FullStack.repository.ThemesRepository;
import com.p6FullStack.repository.UsersRepository;
import lombok.Data;

@Data
@Service
public class ThemesService {

	private final ThemesRepository themesRepository;
	private final UsersRepository usersRepository;

	public List<Themes> getAllThemes() {
		return themesRepository.findAll();
	}
	
    public List<Themes> findSubscriptionByUserId(Long userId) {
        Users user = this.usersRepository.findById(userId).orElse(null);
        return this.themesRepository.findThemesBylistUsersIs(user);
    }

    public List<Themes> findUnSubscriptionByUserId(Long userId) {
        Users user = this.usersRepository.findById(userId).orElse(null);
        return this.themesRepository.findThemesBylistUsersIsNotContaining(user);
    }

}
