package org.examdesign.repository;

import org.examdesign.domain.QuestionAnswers;
import org.examdesign.domain.QuestionAnswersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionAnswersRepository extends JpaRepository<QuestionAnswers, QuestionAnswersId> {
	
	@Modifying
	@Query("delete from questionanswers qa where qa.pk.question.id = 1")
	void deleteQuestionAnswers();
}
