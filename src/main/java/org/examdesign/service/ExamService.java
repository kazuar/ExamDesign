package org.examdesign.service;

import org.examdesign.domain.Exam;
import org.examdesign.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExamService {
	
	@Autowired
	private ExamRepository examRepository;
	
	public Boolean create(Exam exam) {
		
		/*
		for (Question question : exam.getQuestions()) {
			
		}
		*/
		
		Exam savedExam = examRepository.save(exam);
		if (savedExam == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean update(Exam exam) {
		
		Exam existingExam = examRepository.findOne(exam.getId());
		
		if (existingExam == null) {
			return false;
		}
		
		// Set the exam name
		existingExam.setExamName(exam.getExamName());
		
		// Set the exam questions
		//existingExam.setQuestions(exam.getQuestions());
		existingExam.setUpdatedQuestions(exam.getQuestions());
		
		Exam savedExam = examRepository.save(existingExam);
		
		if (savedExam == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Exam exam) {
		
		Exam existingExam = examRepository.findOne(exam.getId());
		
		if (existingExam == null) {
			return false;
		}
		
		examRepository.delete(existingExam);
		
		Exam deletedExam = examRepository.findOne(exam.getId());
		if (deletedExam != null) {
			return false;
		}
		
		return true;
	}
}
