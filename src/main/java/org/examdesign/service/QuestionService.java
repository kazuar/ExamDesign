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
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionAnswersRepository questionAnswersRepository;
	
	@Autowired
	private QuestionAnswersService questionAnswersService;
	
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
		
		// Set the answers for the question
		for (QuestionAnswers answer : question.getAnswers()) {
			answer.setQuestion(question);
		}
		
		existingQuestion.setQuestionText(question.getQuestionText());
		existingQuestion.setCategory(question.getCategory());
		existingQuestion.setDifficulty(question.getDifficulty());
		existingQuestion.setQuestionType(question.getQuestionType());
		//existingQuestion.setAnswers(question.getAnswers());
		existingQuestion.setUpdatedAnswer(question.getAnswers());
		
		Question savedQuestion = questionRepository.save(existingQuestion);
		if (savedQuestion == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean deleteQuestionAnswers(Question question) {
		
		Question existingQuestion = questionRepository.findOne(question.getId());
		
		if (existingQuestion == null) {
			return false;
		}
		
		questionAnswersRepository.deleteQuestionAnswers();
		
		return true;
	}
	
	public Boolean delete(Question question) {
		
		Question existingQuestion = questionRepository.findOne(question.getId());
		
		if (existingQuestion == null) {
			return false;
		}
		
		/*
		try {
			questionAnswersRepository.deleteQuestionAnswers();
		} catch (Exception e) {
			// TODO: handle exception
			int i = 1;
			i = i + 1;
		}
		*/
		/*
		//QuestionAnswers answer = existingQuestion.getAnswers().remove(0);
		//questionAnswersService.delete(answer);
		for (QuestionAnswers answer : existingQuestion.getAnswers()) {
			//existingQuestion.getAnswers().remove(answer);
			//questionAnswersService.delete(answer);
			//int hash = answer.hashCode();
			//questionAnswersRepository.delete(answer);
		}
		*/
		questionRepository.delete(existingQuestion);
		
		Question deletedQuestion = questionRepository.findOne(question.getId());
		if (deletedQuestion != null) {
			return false;
		}

		return true;
	}
}
