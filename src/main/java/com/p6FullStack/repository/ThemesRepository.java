package com.p6FullStack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.p6FullStack.model.Themes;
import com.p6FullStack.model.Users;

@Repository
public interface ThemesRepository extends JpaRepository<Themes, Long> {

    List<Themes> findThemesBylistUsersIs(Users user);

    List<Themes> findThemesBylistUsersIsNotContaining(Users user);

}
