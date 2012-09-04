package org.examdesign.service;

import org.examdesign.domain.Answer;
import org.examdesign.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public Boolean create(Answer answer) {
		
		Answer savedAnswer = answerRepository.save(answer);
		if (savedAnswer == null) {
			return false;
		}
		
		return true;
	}
	
	public Answer createAndReturn(Answer answer) {
		Answer savedAnswer = answerRepository.save(answer);
		return savedAnswer;
	}
	
	public Boolean update(Answer answer) {
		
		Answer existingAnswer = answerRepository.findOne(answer.getId());
		
		if (existingAnswer == null) {
			return false;
		}
		
		existingAnswer.setAnswerText(answer.getAnswerText());
		
		Answer savedAnswer = answerRepository.save(existingAnswer);
		if (savedAnswer == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Answer answer) {
		
		Answer existingAnswer = answerRepository.findOne(answer.getId());
		
		if (existingAnswer == null) {
			return false;
		}
		
		answerRepository.delete(existingAnswer);
		
		Answer deletedAnswer = answerRepository.findOne(answer.getId());
		if (deletedAnswer != null) {
			return false;
		}
		
		return true;
	}
}
