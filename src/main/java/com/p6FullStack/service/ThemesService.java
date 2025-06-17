package com.p6FullStack.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.p6FullStack.model.Themes;
import com.p6FullStack.repository.ThemesRepository;
import lombok.Data;

@Data
@Service
public class ThemesService {

	private final ThemesRepository themesRepository;

	public List<Themes> getAllThemes() {
		return themesRepository.findAll();
	}
}
