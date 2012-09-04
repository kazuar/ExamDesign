package org.examdesign.repository;

import org.examdesign.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	Page<Question> findByQuestionTextLike(String questionText, Pageable pageable);
	
	@Query("select q from question q where q.category.id = :category")
	Page<Question> findByCategory(@Param("category") Long category, Pageable pageable);
	
	@Query("select q from question q where q.difficulty.id = :difficulty")
	Page<Question> findByDifficulty(@Param("difficulty") Long difficulty, Pageable pageable);
	
	@Query("select q from question q where q.questionType.id = :questiontype")
	Page<Question> findByQuestionType(@Param("questiontype") Long questionType, Pageable pageable);
}
