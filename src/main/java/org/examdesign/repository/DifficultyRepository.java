package org.examdesign.repository;

import org.examdesign.domain.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
	
	Difficulty findByDifficultyName(String difficultyName);
	
	Page<Difficulty> findByDifficultyNameLike(String difficultyName, Pageable pageable);
}
