package com.p6FullStack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.p6FullStack.model.Themes;

@Repository
public interface ThemesRepository extends JpaRepository<Themes, Long> {

}
