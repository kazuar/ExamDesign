package org.examdesign.service;

import org.examdesign.domain.Question;
import org.examdesign.domain.QuestionAnswers;
import org.examdesign.repository.QuestionAnswersRepository;
import org.examdesign.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuestionAnswersService {

	@Autowired
	private QuestionAnswersRepository questionAnswersRepository;
	
	/*
	public Boolean create(Question question) {
		
		for (QuestionAnswers answer : question.getAnswers()) {
			answer.setQuestion(question);
		}
		
		Question savedQuestion = questionRepository.save(question);
		if (savedQuestion == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean update(Question question) {
		
		Question existingQuestion = questionRepository.findOne(question.getId());
		
		if (existingQuestion == null) {
			return false;
		}
		
		existingQuestion.setQuestionText(question.getQuestionText());
		
		Question savedQuestion = questionRepository.save(existingQuestion);
		if (savedQuestion == null) {
			return false;
		}
		
		return true;
	}
	*/
	public Boolean delete(QuestionAnswers questionAnswers) {
		
		QuestionAnswers existingQuestionAnswers = questionAnswersRepository.findOne(questionAnswers.getPk());
		
		if (existingQuestionAnswers == null) {
			return false;
		}
		
		questionAnswersRepository.delete(existingQuestionAnswers);
		
		QuestionAnswers deletedQuestionAnswers = questionAnswersRepository.findOne(questionAnswers.getPk());
		if (deletedQuestionAnswers != null) {
			return false;
		}

		return true;
	}
}
