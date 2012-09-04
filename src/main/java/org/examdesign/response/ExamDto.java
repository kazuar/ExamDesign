package org.examdesign.response;

import java.io.Serializable;
import java.util.List;

import org.examdesign.domain.Question;

public class ExamDto implements Serializable {
	
	private Long id;
	private String examName;
	private List<Question> questions;
	
	public Long getId() {
		return id;
	}
	
	public String getExamName() {
		return examName;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
