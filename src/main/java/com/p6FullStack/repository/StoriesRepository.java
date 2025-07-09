package com.p6FullStack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.p6FullStack.model.Stories;

@Repository
public interface StoriesRepository extends JpaRepository<Stories, Long> {

}
