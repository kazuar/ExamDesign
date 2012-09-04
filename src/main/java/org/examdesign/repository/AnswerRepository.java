package org.examdesign.repository;

import org.examdesign.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	Answer findByAnswerText(String answerText);
	
	Page<Answer> findByAnswerTextLike(String answerText, Pageable pageable);
}
