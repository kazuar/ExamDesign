package org.examdesign.repository;

import org.examdesign.domain.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
	
	QuestionType findByQuestionTypeName(String questionTypeName);
	
	Page<QuestionType> findByQuestionTypeNameLike(String questionTypeName, Pageable pageable);
}
