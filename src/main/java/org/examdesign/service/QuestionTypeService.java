package org.examdesign.service;

import java.util.List;

import org.examdesign.domain.QuestionType;
import org.examdesign.repository.QuestionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuestionTypeService {
	
	@Autowired
	private QuestionTypeRepository questionTypeRepository;
	
	public Boolean create(QuestionType questionType) {
		
		QuestionType savedQuestionType = questionTypeRepository.save(questionType);
		if (savedQuestionType == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean update(QuestionType questionType) {
		
		QuestionType existingQuestionType = questionTypeRepository.findOne(questionType.getId());
		
		if (existingQuestionType == null) {
			return false;
		}
		
		existingQuestionType.setQuestionTypeName(questionType.getQuestionTypeName());
		
		QuestionType savedQuestionType = questionTypeRepository.save(existingQuestionType);
		if (savedQuestionType == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(QuestionType questionType) {
		
		QuestionType existingQuestionType = questionTypeRepository.findOne(questionType.getId());
		
		if (existingQuestionType == null) {
			return false;
		}
		
		questionTypeRepository.delete(existingQuestionType);
		
		QuestionType deletedQuestionType = questionTypeRepository.findOne(questionType.getId());
		if (deletedQuestionType != null) {
			return false;
		}
		
		return true;
	}
	
	public String list() {
		List<QuestionType> questionTypes = questionTypeRepository.findAll();
		String questionTypesList = "";
		
		for (QuestionType questionType : questionTypes) {
			questionTypesList += "" + questionType.getId() + ":" + questionType.getQuestionTypeName();
			questionTypesList += (questionTypes.indexOf(questionType) < (questionTypes.size() - 1) ? ";" : "");
		}
		
		return questionTypesList;
	}
}
